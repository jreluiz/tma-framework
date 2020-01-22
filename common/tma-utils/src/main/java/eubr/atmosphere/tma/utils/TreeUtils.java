package eubr.atmosphere.tma.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import eubr.atmosphere.tma.entity.qualitymodel.Attribute;
import eubr.atmosphere.tma.entity.qualitymodel.CompositeAttribute;
import eubr.atmosphere.tma.entity.qualitymodel.CompositeRule;
import eubr.atmosphere.tma.entity.qualitymodel.ConfigurationProfile;
import eubr.atmosphere.tma.entity.qualitymodel.Preference;
import eubr.atmosphere.tma.entity.qualitymodel.Rule;

public class TreeUtils {

	private static final TreeUtils INSTANCE = new TreeUtils();
	
	private TreeUtils() {}

	public static TreeUtils getInstance() {
		return INSTANCE;
	}
	
	/** 
	 * Get root attribute, that is, the attribute that has no parent
	 * @param configurationActor
	 * @return
	 */
	public CompositeAttribute getRootAttribute(ConfigurationProfile configurationActor) {
		for (Preference preference : configurationActor.getPreferences()) {
			Attribute attr = preference.getAttribute();
			if ( isRootAttribute(attr) ) { // attribute is his own composite
				return (CompositeAttribute) attr;
			}
		}
		return null;
	}
	
	public boolean isRootAttribute(Attribute attribute) {
		if (attribute.getAttributeId() == attribute.getCompositeattribute().getAttributeId()) { // attribute is his own composite
			return true;
		}
		return false;
	}
	
	/**
	 * Get root rules, that is, the rules that has no parent
	 * @param rules
	 * @return
	 */
	public List<CompositeRule> getRootRules(Set<Rule> rules) {
		List<CompositeRule> rootRulesList = new ArrayList<>();
		for (Rule rule : rules) {
			if ( isRootRule(rule) ) {
				rootRulesList.add((CompositeRule) rule);
			}
		}
		return rootRulesList;
	}
	
	public boolean isRootRule(Rule rule) {
		if (rule.getRuleId() == rule.getCompositeRule().getRuleId()) { // rule is his own composite
			return true;
		}
		return false;
	}
}
