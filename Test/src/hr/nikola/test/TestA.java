package hr.nikola.test;



public class TestA {
	
	public enum Season {   WINTER, SPRING, SUMMER, FALL }
	
	
    /** <b>LISTA_ZA_STECAJNE_POVJERENIKE</b>. */
    public static final Long LISTA_ZA_STECAJNE_POVJERENIKE = 2L;

    /** <b>LISTA_HRVATSKE_ODVJETNICKE_KOMORE</b>. */
    public static final Long LISTA_HRVATSKE_ODVJETNICKE_KOMORE = 3L;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		compareInts();
		
		compareLongs();
		
		seasons();

	}
	
	private static void seasons() {
		for(Season season: Season. values ()) 
		{   System. out .println(season.name() + " " + season.ordinal()); }
	}
	
	
	
	
	
	private static void compareInts() {
		
		int A = 0;
		int B = 0;
		
		if(A !=  B ) {
			System.out.println("CCCCCCCCCCCCCCCC");
		}else {
			System.out.println("XXXXXXXXXXXXXXXX");
		}
		
	}
	
	private static void compareLongs() {
		
		Long tipUlogeId = 2L;
		
		
        if(tipUlogeId != null && (tipUlogeId.compareTo( LISTA_ZA_STECAJNE_POVJERENIKE ) == 0  
        		|| tipUlogeId.compareTo( LISTA_HRVATSKE_ODVJETNICKE_KOMORE ) == 0 )){

        	System.out.println("AAAAAAAAAAAAAAAA");
         }
	}
	

}
