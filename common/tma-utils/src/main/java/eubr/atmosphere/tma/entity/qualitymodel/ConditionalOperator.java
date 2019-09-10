package eubr.atmosphere.tma.entity.qualitymodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public enum ConditionalOperator {

	/**
     * The value is not equal to
     */
    NOT_EQUAL_TO(0, "Not equal to", "!=", (new ArrayList<Class>()
    {
      {
        add(String.class);
		add(Double.class);
		add(Float.class);
		add(Integer.class);
		add(Short.class);
		add(Long.class);
		add(Date.class);
      }
    })),
    /**
     * The value is equal to
     */
    EQUAL_TO(1, "Equal to", "==", (new ArrayList<Class>()
    {
      {
        add(String.class);
		add(Double.class);
		add(Float.class);
		add(Integer.class);
		add(Short.class);
		add(Long.class);
		add(Date.class);
      }
    })),
    /**
     * The value contains
     */
    CONTAINS(2, "Contains this", "?", (new ArrayList<Class>()
    {
      {
        add(String.class);
      }
    })),
    /**
     * The value is greater than
     */
    GREATER_THAN(3, "Greater than", ">", (new ArrayList<Class>()
    {
      {
    	add(String.class);
		add(Double.class);
		add(Float.class);
		add(Integer.class);
		add(Short.class);
		add(Long.class);
		add(Date.class);
      }
    })),
    /**
     * The value is less than
     */
    LESS_THAN(4, "Less than", "<", (new ArrayList<Class>()
    {
      {
    	add(String.class);
		add(Double.class);
		add(Float.class);
		add(Integer.class);
		add(Short.class);
		add(Long.class);
		add(Date.class);
      }
    })),
    /**
     * The value is greater or equal to
     */
    GREATER_THAN_OR_EQUAL_TO(5, "Greater or equal to", ">=", (new ArrayList<Class>()
    {
      {
    	add(String.class);
		add(Double.class);
		add(Float.class);
		add(Integer.class);
		add(Short.class);
		add(Long.class);
		add(Date.class);
      }
    })),
    /**
     * The value is less or equal to
     */
    LESS_THAN_OR_EQUAL_TO(6, "Less or equal to", "<=", (new ArrayList<Class>()
    {
      {
    	add(String.class);
		add(Double.class);
		add(Float.class);
		add(Integer.class);
		add(Short.class);
		add(Long.class);
		add(Date.class);
      }
    }));

	/**
	 * valor for operator
	 */
	private final Integer valor;
	/**
	 * Description for operator
	 */
	private final String description;
	/**
	 * Language operation
	 */
	private final String operation;
	/**
	 * List of applicable classes.
	 */
	private final List<Class> acceptables;
    
	private ConditionalOperator(Integer valor, String description, String operation, List<Class> acceptables) {
		this.valor = valor;
		this.description = description;
		this.operation = operation;
		this.acceptables = acceptables;
	}

	@Override
	public String toString() {
		StringBuilder me = new StringBuilder("[" + this.getClass().getName());
		me.append(" | name = ");
		me.append(name());
		me.append(" | description = ");
		me.append(description);
		me.append(" | operation = ");
		me.append(operation);
		me.append(" | acceptables = ");
		me.append(Arrays.toString(acceptables.toArray()));
		me.append("]");

		return me.toString();
	}

	public Integer getValor() {
		return valor;
	}

	public String getDescription() {
		return description;
	}

	public String getOperation() {
		return operation;
	}

	/**
	 * Indicates when the specified Class is comparable using this operator.
	 * 
	 * @param clazz Class to verify.
	 * @return True when this operator can be used.
	 */
	public boolean isComparable(Class clazz) {
		for (Class accept : acceptables) {
			if (accept.equals(clazz)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Gets the operator related to description.
	 *
	 * @param description Description for an operation.
	 * @return Type of operator.
	 * @throws EnumConstantNotPresentException When the description is not related
	 *                                         to a valid operator.
	 */
	public static ConditionalOperator fromDescription(String description) throws EnumConstantNotPresentException {
		for (ConditionalOperator operator : ConditionalOperator.values()) {
			if (operator.getDescription().equals(description)) {
				return operator;
			}
		}

		throw new EnumConstantNotPresentException(ConditionalOperator.class, "? (" + description + ")");
	}

	public static ConditionalOperator getEnumByValor(Integer valor) {
		if (valor == null) {
			return null;
		}
		for (ConditionalOperator item : ConditionalOperator.values()) {
			if (item.getValor().intValue() == valor.intValue()) {
				return item;
			}
		}
		return null;
	}
    
 }