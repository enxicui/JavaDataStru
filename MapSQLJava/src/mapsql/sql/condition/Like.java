package mapsql.sql.condition;

import java.util.Map;

import mapsql.sql.core.Field;
import mapsql.sql.core.SQLException;
import mapsql.sql.core.TableDescription;
import mapsql.sql.field.CHARACTER;

public class Like extends AbstractCondition {
	private String column;
	private String value;
	
	public Like(String column, String value) {
		this.column = column;
		this.value = value;
	}

	@SuppressWarnings("unchecked")
	@Override 
	public boolean evaluate(TableDescription description, Map<String, String> data) throws SQLException {
		if (value.charAt(0)== '%' && value.charAt(value.length() - 1 )== '%') {
			if(data.get(column).contains(value.substring(1, value.length()-1)))
					return true;
		}else if (value.charAt(0) == '%') {
			if (data.get(column).contains(value.substring(1,value.length() )))
					return true;
		}else if (value.charAt(value.length() - 1) == '%') {
			if(data.get(column).contains(value.substring(0,value.length()-1)))
				return true;
		}
		return false;
	}
		
}
