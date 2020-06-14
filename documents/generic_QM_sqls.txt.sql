USE knowledge;

-- -- Quality Model -- --

DROP TABLE IF EXISTS HistoricalData;
DROP TABLE IF EXISTS Preference;
DROP TABLE IF EXISTS ConfigurationData;
DROP TABLE IF EXISTS Conditional;
DROP TABLE IF EXISTS LeafRule;
DROP TABLE IF EXISTS Data;
DROP TABLE IF EXISTS ActionPlan;
DROP TABLE IF EXISTS Plan;
DROP TABLE IF EXISTS Configuration;
DROP TABLE IF EXISTS ActionRule;
DROP TABLE IF EXISTS Rule;
DROP TABLE IF EXISTS CompositeRule;
DROP TABLE IF EXISTS Attribute;
DROP TABLE IF EXISTS Metric;
DROP TABLE IF EXISTS LeafAttribute;
DROP TABLE IF EXISTS CompositeAttribute;
DROP TABLE IF EXISTS ConfigurationProfile;
DROP TABLE IF EXISTS Probe;
DROP TABLE IF EXISTS Resource;
DROP TABLE IF EXISTS Description;
DROP TABLE IF EXISTS LeafAttribute;
DROP TABLE IF EXISTS Configuration;
DROP TABLE IF EXISTS Actuator;

CREATE TABLE Attribute (
   attributeId INT NOT NULL AUTO_INCREMENT,
   compositeattributeId INT NOT NULL,
   name VARCHAR(1024),
   primary key(attributeId)  
);

CREATE TABLE CompositeAttribute (
   attributeId INT NOT NULL,
   operator INT,
   PRIMARY KEY (attributeId)
);

ALTER TABLE Attribute ADD CONSTRAINT FK_Attribute_0 FOREIGN KEY (compositeattributeId) REFERENCES CompositeAttribute (attributeId);

CREATE TABLE LeafAttribute (
   attributeId INT NOT NULL,
   normalizationMin DOUBLE PRECISION,
   normalizationMax DOUBLE PRECISION,
   operator INT,
   numSamples INT,
   normalizationKind INT,
   PRIMARY KEY (attributeId)
);

CREATE TABLE HistoricalData (
   historicalDataId INT NOT NULL AUTO_INCREMENT,
   instant TIMESTAMP(6) NOT NULL,
   value DOUBLE PRECISION,
   attributeId INT NOT NULL,
   PRIMARY KEY (historicalDataId, instant)
);

ALTER TABLE HistoricalData ADD CONSTRAINT FK_HistoricalData_0 FOREIGN KEY (attributeId) REFERENCES Attribute (attributeId);

CREATE TABLE ConfigurationProfile (
   configurationprofileId INT NOT NULL AUTO_INCREMENT,
   active BOOLEAN,
   PRIMARY KEY (configurationprofileId)
);

CREATE TABLE Preference (
   preferenceId INT NOT NULL,
   attributeId INT NOT NULL,
   configurationprofileId INT NOT NULL,
   weight DOUBLE PRECISION,
   threshold DOUBLE PRECISION,
   PRIMARY KEY (preferenceId)
);

ALTER TABLE Preference ADD CONSTRAINT FK_Preference_0 FOREIGN KEY (ConfigurationProfileId) REFERENCES ConfigurationProfile (configurationprofileId);
ALTER TABLE Preference ADD CONSTRAINT FK_Preference_1 FOREIGN KEY (AttributeId) REFERENCES Attribute (attributeId);

CREATE TABLE Actuator (
    actuatorId INT NOT NULL AUTO_INCREMENT,
    address VARCHAR(1024),
    pubKey VARCHAR(1024),
    PRIMARY KEY (actuatorId)
);

CREATE TABLE Resource (
    resourceId INT NOT NULL AUTO_INCREMENT,
    resourceName VARCHAR(128),
    resourceType VARCHAR(16),
    resourceAddress VARCHAR(1024),
    PRIMARY KEY (resourceId)
);

CREATE TABLE Probe (
    probeId INT NOT NULL AUTO_INCREMENT,
    probeName VARCHAR(128),
    password VARCHAR(128),
    salt VARCHAR(128) NOT NULL,
    token VARCHAR(256),
    tokenExpiration TIMESTAMP(6),
    PRIMARY KEY (probeId)
);

CREATE TABLE Description (
    descriptionId INT NOT NULL AUTO_INCREMENT,
    dataType VARCHAR(128),
    descriptionName VARCHAR(128),
    unit VARCHAR(128),
    PRIMARY KEY (descriptionId)
);

CREATE TABLE Metric (
   attributeId INT NOT NULL,
   configurationprofileId INT NOT NULL,
   descriptionId INT NOT NULL,
   probeId INT NOT NULL,
   resourceId INT NOT NULL,
   probeName VARCHAR(1024),
   descriptionName VARCHAR(1024),
   resourceName VARCHAR(1024),
   PRIMARY KEY (attributeId)
);

ALTER TABLE Metric ADD CONSTRAINT FK_Metric_0 FOREIGN KEY (configurationprofileId) REFERENCES ConfigurationProfile (configurationprofileId);
ALTER TABLE Metric ADD CONSTRAINT FK_Metric_1 FOREIGN KEY (attributeId) REFERENCES LeafAttribute (attributeId);
ALTER TABLE Metric ADD CONSTRAINT FK_Metric_2 FOREIGN KEY (descriptionId) REFERENCES Description (descriptionId);
ALTER TABLE Metric ADD CONSTRAINT FK_Metric_3 FOREIGN KEY (probeId) REFERENCES Probe (probeId);
ALTER TABLE Metric ADD CONSTRAINT FK_Metric_4 FOREIGN KEY (resourceId) REFERENCES Resource (resourceId);

CREATE TABLE Data (
    probeId INT NOT NULL,
    descriptionId INT NOT NULL,
    resourceId INT NOT NULL,
    metricId INT NOT NULL,
    valueTime TIMESTAMP(6) NOT NULL,
    value DOUBLE PRECISION,
    PRIMARY KEY (probeId,descriptionId,resourceId,valueTime)
);

ALTER TABLE Data ADD CONSTRAINT FK_Data_0 FOREIGN KEY (probeId) REFERENCES Probe (probeId);
ALTER TABLE Data ADD CONSTRAINT FK_Data_1 FOREIGN KEY (descriptionId) REFERENCES Description (descriptionId);
ALTER TABLE Data ADD CONSTRAINT FK_Data_2 FOREIGN KEY (resourceId) REFERENCES Resource (resourceId);
ALTER TABLE Data ADD CONSTRAINT FK_Data_3 FOREIGN KEY (metricId) REFERENCES Metric (attributeId);

-- Drools Rules

CREATE TABLE Rule (
   ruleId INT NOT NULL AUTO_INCREMENT,
   compositeRuleId INT NOT NULL,
   name VARCHAR(1024),
   attributeId INT,
   enabled BOOLEAN,
   priority INT,
   activationGroup VARCHAR(1024),
   primary key(ruleId)  
);

CREATE TABLE CompositeRule (
   ruleId INT NOT NULL,
   PRIMARY KEY (ruleId)
);

ALTER TABLE Rule ADD CONSTRAINT FK_Rule_0 FOREIGN KEY (compositeRuleId) REFERENCES CompositeRule (ruleId);
ALTER TABLE Rule ADD CONSTRAINT FK_Rule_1 FOREIGN KEY (attributeId) REFERENCES Attribute (attributeId);

CREATE TABLE LeafRule (
   ruleId INT NOT NULL,
   PRIMARY KEY (ruleId)
);

CREATE TABLE Conditional (
    conditionId INT NOT NULL AUTO_INCREMENT,
    ruleId INT NOT NULL,
    conditionalOperator INT,
    value VARCHAR(1024),
    PRIMARY KEY (conditionId)
);

ALTER TABLE Conditional ADD CONSTRAINT FK_Condition_1 FOREIGN KEY (ruleId) REFERENCES Rule (ruleId);

CREATE TABLE ActionRule (
    actionRuleId INT NOT NULL AUTO_INCREMENT,
    ruleId INT NOT NULL,
    actuatorId INT NOT NULL,
    resourceId INT NOT NULL,
    actionName VARCHAR(128),
    PRIMARY KEY (actionRuleId)
);

ALTER TABLE ActionRule ADD CONSTRAINT FK_ActionRule_1 FOREIGN KEY (ruleId) REFERENCES Rule (ruleId);
ALTER TABLE ActionRule ADD CONSTRAINT FK_Actuator_1 FOREIGN KEY (actuatorId) REFERENCES Actuator (actuatorId);
ALTER TABLE ActionRule ADD CONSTRAINT FK_Resource_1 FOREIGN KEY (resourceId) REFERENCES Resource (resourceId);

-- Planning

CREATE TABLE Plan (
    planId INT NOT NULL AUTO_INCREMENT,
    valueTime TIMESTAMP(6) NOT NULL,
    status INT,
    PRIMARY KEY (planId)
);

CREATE TABLE ActionPlan (
    planId INT NOT NULL,
    actionRuleId INT NOT NULL,
    executionOrder INT,
    status INT,
    PRIMARY KEY (planId, actionRuleId)
);

ALTER TABLE ActionPlan ADD CONSTRAINT FK_ActionPlan_0 FOREIGN KEY (planId) REFERENCES Plan (planId);
ALTER TABLE ActionPlan ADD CONSTRAINT FK_ActionPlan_1 FOREIGN KEY (actionRuleId) REFERENCES ActionRule (actionRuleId);

CREATE TABLE Configuration (
    configurationId INT NOT NULL AUTO_INCREMENT,
    actionRuleId INT NOT NULL,
    keyName VARCHAR(128),
    domain VARCHAR(1024),
    value VARCHAR(1024),
    PRIMARY KEY (configurationId, actionRuleId)
);

ALTER TABLE Configuration ADD CONSTRAINT FK_Configuration_0 FOREIGN KEY (actionRuleId) REFERENCES ActionRule (actionRuleId);

CREATE TABLE ConfigurationData (
    planId INT NOT NULL,
    actionRuleId INT NOT NULL,
    configurationId INT NOT NULL,
    value VARCHAR(1024),
    PRIMARY KEY (planId, actionRuleId, configurationId)
    FOREIGN KEY (planId, actionRuleId) REFERENCES ActionPlan (planId, actionRuleId),
    FOREIGN KEY (actionRuleId, configurationId) REFERENCES Configuration (actionRuleId, configurationId)
);

-- ALTER TABLE ConfigurationData ADD CONSTRAINT FK_ConfigurationData_0 FOREIGN KEY (planId, actionRuleId) REFERENCES ActionPlan (planId, actionRuleId);
-- ALTER TABLE ConfigurationData ADD CONSTRAINT FK_ConfigurationData_1 FOREIGN KEY (actionRuleId, configurationId) REFERENCES Configuration (actionRuleId, configurationId);

-- Privacy and Performance QM

insert into CompositeAttribute (attributeId, operator) values (1,0);
insert into CompositeAttribute (attributeId, operator) values (2,0);
insert into CompositeAttribute (attributeId, operator) values (5,0);
insert into CompositeAttribute (attributeId, operator) values (6,0);

insert into Attribute (attributeId, compositeattributeId, name) values (1, 1, 'TRUSTWORTHINESS');
insert into Attribute (attributeId, compositeattributeId, name) values (2, 1, 'PRIVACY');
insert into Attribute (attributeId, compositeattributeId, name) values (3, 2, 'INFORMATIONLOSS');
insert into Attribute (attributeId, compositeattributeId, name) values (4, 2, 'REIDENTIFICATIONRISK');
insert into Attribute (attributeId, compositeattributeId, name) values (5, 1, 'RESOURCECONSUMPTIONOFTHEPRIVAAASPOD');
insert into Attribute (attributeId, compositeattributeId, name) values (6, 5, 'TOTALRESOURCECONSUMPTION');
insert into Attribute (attributeId, compositeattributeId, name) values (7, 6, 'RATIOOFCPUUSAGEOFTHEPRIVAAASPOD');
insert into Attribute (attributeId, compositeattributeId, name) values (8, 6, 'RATIOOFMEMORYUSAGEOFTHEPRIVAAASPOD');
insert into Attribute (attributeId, compositeattributeId, name) values (9, 5, 'PODSCOUNT');

insert into LeafAttribute (attributeId, normalizationMin, normalizationMax, operator, numSamples, normalizationKind) values (3, 0.0, 1.0, 0, 1, 1);
insert into LeafAttribute (attributeId, normalizationMin, normalizationMax, operator, numSamples, normalizationKind) values (4, 0.0, 1.0, 0, 1, 1);
insert into LeafAttribute (attributeId, normalizationMin, normalizationMax, operator, numSamples, normalizationKind) values (7, 0.0, 1.0, 0, 1, 1);
insert into LeafAttribute (attributeId, normalizationMin, normalizationMax, operator, numSamples, normalizationKind) values (8, 0.0, 1.0, 0, 1, 1);
insert into LeafAttribute (attributeId, normalizationMin, normalizationMax, operator, numSamples, normalizationKind) values (9, 0.0, 10.0, 0, 1, 0);

insert into ConfigurationProfile (configurationprofileId, active) values (1, true);

insert into Preference (preferenceId, attributeId, configurationprofileId, weight, threshold) values (1, 1, 1, 1, 0.9);
insert into Preference (preferenceId, attributeId, configurationprofileId, weight, threshold) values (2, 1, 1, 0.5, 0.9390);
insert into Preference (preferenceId, attributeId, configurationprofileId, weight, threshold) values (3, 2, 1, 0.1, 0.7);
insert into Preference (preferenceId, attributeId, configurationprofileId, weight, threshold) values (4, 3, 1, 0.9, 0.05);
insert into Preference (preferenceId, attributeId, configurationprofileId, weight, threshold) values (5, 5, 1, 0.5, 0.95);
insert into Preference (preferenceId, attributeId, configurationprofileId, weight, threshold) values (6, 6, 1, 1, 0.9);
insert into Preference (preferenceId, attributeId, configurationprofileId, weight, threshold) values (7, 7, 1, 0.65, 0.8);
insert into Preference (preferenceId, attributeId, configurationprofileId, weight, threshold) values (8, 8, 1, 0.35, 0.5);
insert into Preference (preferenceId, attributeId, configurationprofileId, weight, threshold) values (9, 9, 1, 1, 100);

insert into Probe (probeId, probeName, password, salt, token, tokenExpiration) values (8, 'PRIVAaaS', '', '', '', '2020-05-26 00:00:00.000000');
insert into Resource (resourceId, resourceName, resourceType, resourceAddress) values (8, 'PRIVAaaS', 'Privacy', '');
insert into Description (descriptionId, dataType, descriptionName, unit) values (30, 'int', 'k', '');
insert into Description (descriptionId, dataType, descriptionName, unit) values (31, 'double', 'riskP', '');
insert into Description (descriptionId, dataType, descriptionName, unit) values (32, 'double', 'riskJ', '');
insert into Description (descriptionId, dataType, descriptionName, unit) values (33, 'double', 'riskM', '');
insert into Description (descriptionId, dataType, descriptionName, unit) values (34, 'double', 'score', '');
insert into Description (descriptionId, dataType, descriptionName, unit) values (35, 'double', 'id', '');

insert into Probe (probeId, probeName, password, salt, token, tokenExpiration) values (101, 'K8s', '', '', '', '2020-05-26 00:00:00.000000');
insert into Resource (resourceId, resourceName, resourceType, resourceAddress) values (102, 'K8s', 'Performance', '');
insert into Description (descriptionId, dataType, descriptionName, unit) values (103, 'double', 'cpu_usage', '');
insert into Description (descriptionId, dataType, descriptionName, unit) values (104, 'double', 'memory_usage', '');
insert into Description (descriptionId, dataType, descriptionName, unit) values (105, 'double', 'pod_count', '');

insert into Metric (attributeId, configurationprofileId, descriptionId, probeId, resourceId, probeName, descriptionName, resourceName) values (3, 1, 34, 8, 8, 'probe PRIVAaaS', 'InformationLossMetric','anonymizator');
insert into Metric (attributeId, configurationprofileId, descriptionId, probeId, resourceId, probeName, descriptionName, resourceName) values (4, 1, 31, 8, 8, 'probe PRIVAaaS', 'ReIdentificationRiskMetric','anonymizator');
insert into Metric (attributeId, configurationprofileId, descriptionId, probeId, resourceId, probeName, descriptionName, resourceName) values (7, 1, 103, 101, 102, 'probe K8s', 'Ratio of CPU usage of the PRIVAaaS Pod','CPU usage');
insert into Metric (attributeId, configurationprofileId, descriptionId, probeId, resourceId, probeName, descriptionName, resourceName) values (8, 1, 104, 101, 102, 'probe K8s', 'Ratio of Memory usage of the PRIVAaaS Pod','Memory usage');
insert into Metric (attributeId, configurationprofileId, descriptionId, probeId, resourceId, probeName, descriptionName, resourceName) values (9, 1, 105, 101, 102, 'probe K8s', 'Pods Count','Pod Count');

-- Rules of privacy QM

insert into CompositeRule (ruleId) values (1);
insert into CompositeRule (ruleId) values (4);

insert into Rule (ruleId, compositeRuleId, name, attributeId, enabled, priority, activationGroup) values (1, 1, "Score less than Threshold", 2, true, 1, "Privacy Group");
insert into Rule (ruleId, compositeRuleId, name, attributeId, enabled, priority, activationGroup) values (2, 1, "Increase k-anonimity by 1", 2, true, 2, "Privacy Group");
insert into Rule (ruleId, compositeRuleId, name, attributeId, enabled, priority, activationGroup) values (3, 1, "Multiply k-anonimity by 2", 2, true, 2, "Privacy Group");

insert into Rule (ruleId, compositeRuleId, name, attributeId, enabled, priority, activationGroup) values (4, 4, "Score greater than or equal to Threshold", 2, true, 2, "Privacy Group");

insert into LeafRule (ruleId) values (2);
insert into LeafRule (ruleId) values (3);

insert into Conditional (conditionId, ruleId, conditionalOperator, value) values (1, 1, 4, "threshold"); -- "LESS_THAN"
insert into Conditional (conditionId, ruleId, conditionalOperator, value) values (2, 2, 3, "previousScore"); -- "GREATER_THAN"
insert into Conditional (conditionId, ruleId, conditionalOperator, value) values (3, 3, 1, "previousScore"); -- "EQUAL_TO"
insert into Conditional (conditionId, ruleId, conditionalOperator, value) values (4, 4, 5, "threshold"); -- "GREATER_THAN_OR_EQUAL_TO"

insert into Actuator (actuatorId, address, pubKey) values (1, "Privacy anonymizator", "TODO - update");

insert into ActionRule (actionRuleId, ruleId, actuatorId, resourceId, actionName) values (1, 1, 1, 8, "Increase k-anonimity by 1");
insert into ActionRule (actionRuleId, ruleId, actuatorId, resourceId, actionName) values (2, 2, 1, 8, "Increase k-anonimity by 1");
insert into ActionRule (actionRuleId, ruleId, actuatorId, resourceId, actionName) values (3, 3, 1, 8, "Multiply k-anonimity by 2");
insert into ActionRule (actionRuleId, ruleId, actuatorId, resourceId, actionName) values (4, 4, 1, 8, "None");

insert into Configuration (configurationId, actionRuleId, keyName, domain, value) values (1, 1, "k", "float", "increase_by_1");
insert into Configuration (configurationId, actionRuleId, keyName, domain, value) values (2, 2, "k", "float", "increase_by_1");
insert into Configuration (configurationId, actionRuleId, keyName, domain, value) values (3, 3, "k", "float", "multiply_by_2");
insert into Configuration (configurationId, actionRuleId, keyName, domain, value) values (4, 4, "k", "float", "none");

-- Rules of performance QM

insert into CompositeRule (ruleId) values (10);

insert into Rule (ruleId, compositeRuleId, name, attributeId, enabled, priority, activationGroup) values (10, 1, "Score less than Threshold", 5, true, 1, "Performance Group");

insert into Conditional (conditionId, ruleId, conditionalOperator, value) values (10, 10, 4, "threshold"); -- "LESS_THAN"
insert into Conditional (conditionId, ruleId, conditionalOperator, value) values (11, 10, 5, "threshold"); -- "GREATER_THAN_OR_EQUAL_TO"  

insert into Actuator (actuatorId, address, pubKey) values (2, "Performance actuator -> mem", "TODO - update");

insert into ActionRule (actionRuleId, ruleId, actuatorId, resourceId, actionName) values (10, 10, 2, 102, "Increase pod memory");
insert into ActionRule (actionRuleId, ruleId, actuatorId, resourceId, actionName) values (11, 10, 2, 102, "None");

insert into Configuration (configurationId, actionRuleId, keyName, domain, value) values (10, 10, "mem", "double", "increase_mem");
insert into Configuration (configurationId, actionRuleId, keyName, domain, value) values (11, 10, "mem", "double", "none");

-- Data info (mock)

insert into Data (probeId, descriptionId, resourceId, metricId, valueTime, value) values (8, 30, 8, 3, '2019-07-22 00:52:33.000000', 2);
insert into Data (probeId, descriptionId, resourceId, metricId, valueTime, value) values (8, 31, 8, 4, '2019-07-22 00:52:33.000000', 0.5);
insert into Data (probeId, descriptionId, resourceId, metricId, valueTime, value) values (8, 32, 8, 4, '2019-07-22 00:52:33.000000', 0.5);
insert into Data (probeId, descriptionId, resourceId, metricId, valueTime, value) values (8, 33, 8, 4, '2019-07-22 00:52:33.000000', 0.0016976327454494011);
insert into Data (probeId, descriptionId, resourceId, metricId, valueTime, value) values (8, 34, 8, 3, '2019-07-22 00:52:33.000000', 0.270841806083175);
insert into Data (probeId, descriptionId, resourceId, metricId, valueTime, value) values (8, 35, 8, 3, '2019-07-17 00:00:00.000000', 0);

insert into Data (probeId, descriptionId, resourceId, metricId, valueTime, value) values (101, 103, 102, 7, '2020-05-24 00:52:33.000000', 0.76);
insert into Data (probeId, descriptionId, resourceId, metricId, valueTime, value) values (101, 104, 102, 8, '2020-05-25 00:52:33.000000', 0.270841806083175);
insert into Data (probeId, descriptionId, resourceId, metricId, valueTime, value) values (101, 105, 102, 9, '2020-05-26 00:00:00.000000', 6);
