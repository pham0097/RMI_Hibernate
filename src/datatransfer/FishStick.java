package datatransfer;
/* File: FishStick.java
 * Author: Stanley Pieda
 * Date: Jan 2018
 * Modified By: Phuong Pham, Shamarokh Arjumand
 * Modifed On: March 27, 2018
 * Description:JPA annotations for Hibernate to use
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Entity class representing the fishStick table.
 * @author Phuong Pham, Shamarokh Arjumand
 */
@Entity
@Table(name = "fishStick")
public class FishStick implements Serializable{
	/** Explicit serialVersionUID to avoid generating one automatically */
	private static final long serialVersionUID = 1L;
	
	/** ID value for database */
   @Id
   @Column(name = "id")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	/** recordNumber for database, originally matched a dataset file line number */
   @Column(name = "recordNumber", length = 55)
   private int recordNumber;
	
	/** omega field */
   @Column(name = "omega", length = 55)
	private String omega;
	
	/** lambda field */
   @Column(name = "lambda", length = 55)
	private String lambda;
	
	/** uuid field, contains UUID as String */
   @Column(name = "uuid", length = 55)   
	private String uuid;
	
	/** see lab hand-out notes from assignment 1 */
	@Transient
	private boolean isLastFishStick;
	
	/**
	 * Default constructor, sets id and recordNumber to zero, omega, lambda, uuid to empty Strings
	 * @author Stanley Pieda
	 */
	public FishStick() {
		this(0,0,"","","");
	}
	
	/**
	 * Telescoping constructor.
	 * @param id The id as Integer
	 * @param recordNumber The recordNumber as int
	 * @param omega The omega as String
	 * @param lambda The lambda as String
	 * @param uuid The UUID as String
	 * @author Stanley Pieda
	 */
	public FishStick(Integer id, int recordNumber, String omega, String lambda, String uuid) {
		this.id = id;
		this.recordNumber = recordNumber;
		this.omega = omega;
		this.lambda = lambda;
		this.uuid = uuid;
	}
	
	/** Getter for id */
	public Integer getId() {
		return id;
	}
	/** Setter for id */
	public void setId(Integer id) {
		this.id = id;
	}
	/** Getter for recordNumber */

	public int getRecordNumber() {
		return recordNumber;
	}
	/** Setter for recordNumber */
	public void setRecordNumber(int recordNumber) {
		this.recordNumber = recordNumber;
	}
	/** Getter for omega */

	public String getOmega() {
		return omega;
	}
	/** Setter for omega */
	public void setOmega(String omega) {
		this.omega = omega;
	}
	/** Getter for lambda */

	public String getLambda() {
		return lambda;
	}
	/** Setter for lambda */
	public void setLambda(String lambda) {
		this.lambda = lambda;
	}
	/** Getter for uuid */

	public String getUUID() {
		return uuid;
	}
	/** Setter for uuid */
	public void setUUID(String uuid) {
		this.uuid = uuid;
	}
	/** Getter for isLastFishStick, can be used by consumer to detect end of buffer */
	public boolean isLastFishStick() {
		return isLastFishStick;
	}
	/** Setter for isLastFishStick, can be used by producer when placing last FishStick into buffer */
	public void setLastFishStick(boolean isLastFishStick) {
		this.isLastFishStick = isLastFishStick;
	}
	
	/** Overridden toString() to provide formatting for console output. */
	@Override
	public String toString() {
		return String.format("%d, %d, %s, %s, %s", id, recordNumber, omega, lambda, uuid);
	}
}
