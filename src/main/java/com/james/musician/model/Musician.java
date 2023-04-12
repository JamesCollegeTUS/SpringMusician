package com.james.musician.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "musicians")
public class Musician{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="email_address")
	private String emailAddress;
	
	@Min(value = 18, message = "Musician must be 18 years of age or older!!")
	@Max(value = 65, message = "Musician should not be older than 65!!")
	private int age;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private MusicStyle style;
	
	@NotNull(message = "Musician must play at least one instrument!!")
	@Column(name = "instrument_a")
	@Enumerated(EnumType.STRING)
	private Instruments instrumentA;
	
	@Column(name = "instrument_b")
	@Enumerated(EnumType.STRING)
	private Instruments instrumentB;
	
	
//	public Musician() {
//		
//	}
//	public Musician(String firstName, String lastName, String emailAddress, int age, MusicStyle style, 
//			Instruments instrumentA, Instruments instrumentB) {
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.emailAddress = emailAddress;
//		this.age = age;
//		this.style = style;
//		this.instrumentA = instrumentA;
//		this.instrumentA = instrumentB;
//	
//	}
//	

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}

	public MusicStyle getStyle() {
		return style;
	}

	public void setStyle(MusicStyle style) {
		this.style = style;
	}
	public Instruments getInstrumentA() {
		return instrumentA;
	}
	public void setInstrumentA(Instruments instrumentA) {
		this.instrumentA = instrumentA;
	}
	public Instruments getInstrumentB() {
		return instrumentB;
	}
	public void setInstrumentB(Instruments instrumentB) {
		this.instrumentB = instrumentB;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		
		if((obj == null) || (obj.getClass() != this.getClass())){
			return false;
		}
		
		String objName = ((Musician) obj).getEmailAddress();
		return this.emailAddress.equals(objName);
	}
}
