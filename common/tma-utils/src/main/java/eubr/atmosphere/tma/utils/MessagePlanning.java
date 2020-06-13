package eubr.atmosphere.tma.utils;

/**
 * Used to send messages to planning component
 * 
 * @author JorgeLuiz
 */
public class MessagePlanning {

	private Integer configurationProfileId;

	public MessagePlanning(Integer configurationProfileId) {
		super();
		this.configurationProfileId = configurationProfileId;
	}

	public Integer getConfigurationProfileId() {
		return configurationProfileId;
	}

	public void setConfigurationProfileId(Integer configurationProfileId) {
		this.configurationProfileId = configurationProfileId;
	}

	@Override
	public String toString() {
		return "MessagePlanning [configurationProfileId=" + configurationProfileId + "]";
	}
	
}
