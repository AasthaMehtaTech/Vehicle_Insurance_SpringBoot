package com.lti.project.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.lti.project.bean.Claims;
import com.lti.project.bean.Plan;

@Repository
public class ClaimsDaoImlp implements ClaimsDao  {

	@PersistenceContext
	private EntityManager manager;

	public List<Claims> getClaims()
	{
		String strQry = "from Claims";
		Query qry = manager.createQuery(strQry);
		List<Claims> claimList= qry.getResultList();
		return claimList;
		//user method
		
		
	}
	public  boolean claimPolicy(long polNum, long reqamt, String reason)
	{	
		//String strQry ="Select planAmt from Policy pl JOIN  Plan pa WHERE pl.planId==pa.planId"
		//Query qry = manager.createQuery(strQry);
		//String resultPlanAmt= qry.getResult();
		// if (reqamt<=resultPlanAmt && reason.equals("Natural Disaster")) -----
		
		int appramt=0;
		if (reason.equals("Natural Disaster")){
			appramt=0.8*requestedAmt;
		}
		else if (reason.equals("Road Accident")){
			appramt=0.65*requestedAmt;
		}
		else if (reason.equals("Theft")){
			appramt=0.5*requestedAmt;
		}
		else if (reason.equals("Man Made Disaster")){
			appramt=0;
		}
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
	    String strDate = formatter.format(date);
		
		Claims clm=new Claims();
		//clm.setReqAmt(reqamt);
		clm.setReason(reason);
		clm.setApprovAmt(appramt);
		clm.setApprovStatus("Pending");
		clm.setClaimDate(strDate);  //today's date
		clm.setPolicyNum(polNum);
		//user method	
	}
	
	
	public int approveClaim(long reqNum) {
		
		String strQry= "UPDATE  Claims ApprovStatus=:stat WHERE Request_Num=:reqno";
		Query qry = manager.createQuery(strQry);
		qry.setParameter("stat","Approved");
		qry.setParameter("reqno",reqNum);
		int i = qry.executeUpdate();
		return i;
		//admin method	
	}
	
	public int declineClaim(long reqNum) {
		
		String strQry= "UPDATE  Claims ApprovStatus=:stat WHERE Request_Num=:reqno";
		Query qry = manager.createQuery(strQry);
		qry.setParameter("stat","Declined");
		qry.setParameter("reqno",reqNum);
		int i = qry.executeUpdate();
		return i;
		//admin method	
	}
	
	public List<Claims> viewClaims(){
		
		String strQry = "from Claims";
		Query qry = manager.createQuery(strQry);
		List<Claims> claimList= qry.getResultList();
		return claimList;
		//admin method
	}
	
}