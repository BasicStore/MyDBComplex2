package com.hibb.dm;

public enum Gender {
	
	MALE(1, "MALE"),
	FEMALE(2, "FEMALE");
	
	private final int id;
	
	private final String lit;
	
	private Gender(int id, String lit) {
		this.id = id;
		this.lit = lit;
	}

	
	public int getId() {
		return id;
	}
	
	public String getLit() {
		return lit;
	}

	
//	public static Gender fromLit(String inLit) {
//        switch (inLit) {
//        case "MALE":
//            return Gender.MALE;
//        case "FEMALE":
//            return Gender.FEMALE;
// 
//        default:
//            throw new IllegalArgumentException("Literal [" + inLit
//                    + "] not supported.");
//        }
//    }
	
		
	public static Gender fromId(int inId) {
        switch (inId) {
        case 1:
            return Gender.MALE;
        case 2:
            return Gender.FEMALE;
 
        default:
            throw new IllegalArgumentException("Literal [" + inId
                    + "] not supported.");
        }
    }
	
}
