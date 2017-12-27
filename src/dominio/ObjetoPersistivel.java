package dominio;

import java.io.Serializable;

public interface ObjetoPersistivel extends Serializable {

	public int getId();
	
	public void setId(int id);
}
