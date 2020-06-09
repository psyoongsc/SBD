package application;

public class UserData {
	private static final int MAXTEAM = 3;
	private static String userid="";
	private static int[] teamid = new int[MAXTEAM];
	private static String[] teamname = new String[MAXTEAM];
	private static int teamcnt= 0;
	
	public static void setUserid(String uid){
		userid = uid;
	}
	public static String getUserid(){
		return userid;
	}
	
	public static void setTeam(int[] tid, String[] tnm){
		for(int i=0; i<MAXTEAM; i++){
			if(tid[i]>0&&!tnm[i].equals("")){teamid[teamcnt]=tid[teamcnt]; teamname[teamcnt] = tnm[teamcnt]; teamcnt++;}
		}
	}
	
	public static void setTeam(int tid, String tnm){
		teamid[teamcnt] = tid;
		teamname[teamcnt] = tnm;
		teamcnt++;
	}
	
	public static int[] getTeamid(){
		return teamid;
	}
	
	public static int getTeamcnt(){
		return teamcnt;
	}
	
	public static String[] getTeamname(){
		return teamname;
	}
	
	public static void printTeam(){
		for(int i=0; i<teamcnt; i++){
			System.out.print(teamid[i]+":"+teamname[i]+",");
		}
		System.out.println();
	}
}
