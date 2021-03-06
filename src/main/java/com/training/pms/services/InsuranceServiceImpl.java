package com.training.pms.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.pms.models.Insurance;
import com.training.pms.models.InsuranceProvider;
import com.training.pms.models.Patient;
import com.training.pms.repository.InsuranceRepository;

@Service
public class InsuranceServiceImpl implements InsuranceService
{

	@Autowired
	private InsuranceRepository repo;
	
	private static Logger log = Logger.getLogger(InsuranceServiceImpl.class.getName());

	
	@Override
	public String addInsurance(Insurance obj) 
	{
		String msg = "";
		
		try
		{
			if(obj != null)
				repo.save(obj);
			
			msg = "Insurance | Id: " + obj.getInsuranceId()  + " | was added to the database";
			log.info(msg);
		}
		catch (NullPointerException e)
		{
			msg = "Insurance was null in addInsurance() | Error Msg: " + e.getMessage();
			log.error(msg);
		}
		catch (Exception e)
		{
			msg = "Unhandled exception in addInsurance() | " + e.getMessage();
			log.error(msg);
		}
		
		return msg;
	}

	@Override
	public String updateInsurance(Insurance obj) 
	{
		String msg = "";
		try
		{
			repo.save(obj);
			msg = "Insurance | Id: " + obj.getInsuranceId() + " | was updated in the database";
			log.info(msg);
		}
		catch (NullPointerException e)
		{
			msg = "Insurance was null in updateInsurance() | Error Msg: " + e.getMessage();
			log.error(msg);
		}
		catch (Exception e)
		{
			msg = "Unhandled exception in updateInsurance() | " + e.getMessage();
			log.error(msg);
		}
		
		return msg;
	}

	@Override
	public String deleteInsurance(Insurance obj) 
	{
		String msg = "";
		
		try
		{
			repo.delete(obj);
			
			msg = "Insurance | Id: " + obj.getInsuranceId() +  " | was deleted to the database";
			log.info(msg);
		}
		catch (NullPointerException e)
		{
			msg = "Insurance was null in deleteInsurance() | Error Msg: " + e.getMessage();
			log.error(msg);
		}
		catch (Exception e)
		{
			msg = "Unhandled exception in deleteInsurance() | " + e.getMessage();
			log.error(msg);
		}
		
		return msg;
	}

	@Override
	public ArrayList<Insurance> getAllInsurance() 
	{
		ArrayList<Insurance> pList = null;
		
		try
		{
			pList = (ArrayList<Insurance>) repo.findAll();
			
			if(pList == null)
				throw new NullPointerException("InsuranceRepository returned a null list");
			
			log.info("getAllInsurance() called | Result: " + pList);
		}catch(NullPointerException e)
		{
			log.error("NullPointer exception in getAllInsurance() | Error: " + e.getMessage());
		}catch (Exception e)
		{
			log.error("Unhandled exception in getAllInsurance() | Error: " + e.getMessage());
		}
		
		return pList;
	}

	@Override
	public Insurance getInsuranceById(int id) 
	{

		Insurance obj = null;
		try
		{
			Optional<Insurance> temp = repo.findById(id); 	// Getting the patient from the db
			
			if(temp.isEmpty())								// If our result is null throw an error
				throw new NullPointerException();
			else								
			{
				obj = temp.get();							// Otherwise, assign our result;
				log.info("Insurance | Id: " + obj.getInsuranceId() + " | was retrieved by getInsuranceById() ");
			}
		}
		catch (NullPointerException e)
		{
			log.error("Insurance was null in getInsuranceById() | Search Id: " + id + " | Error: " + e.getMessage());
		}
		catch (Exception e)
		{
			log.error("Unhandled error in getInsuranceById() | Search Id: " + id + " | Error: " + e.getMessage());
		}
		return obj;
	}

	@Override
	public List<Insurance> getInsuranceByPatient(Patient obj)
	{
		List<Insurance> rList = null;
		
		try
		{
			List<Insurance> tList = (List<Insurance>)repo.findAll();
			rList = new ArrayList<Insurance>();
			
			for(Insurance temp : tList)
			{
				if(temp.getPatient().getPatientId() == obj.getPatientId())
					rList.add(temp);
			}	
		}
		catch(NullPointerException e)
		{
			log.error("Object was null in getInsuranceByPatient() | Error: " + e.getMessage());
		}
		catch(Exception e)
		{
			log.error("Unhandled error in getInsuranceByPatient() | Search Obj: " + obj + " | Error: " + e.getMessage());
		}
		
		return rList;
	}
	
	@Override
	public List<Insurance> 	getInsuranceByProvider(InsuranceProvider obj)
	{
		List<Insurance> rList = null;
		
		try
		{
			List<Insurance> tList = (List<Insurance>)repo.findAll();
			rList = new ArrayList<Insurance>();
			
			for(Insurance temp : tList)
			{
				if(temp.getProvider().getProviderId() == obj.getProviderId())
					rList.add(temp);
			}	
		}
		catch(NullPointerException e)
		{
			log.error("Object was null in getInsuranceByProvider() | Error: " + e.getMessage());
		}
		catch(Exception e)
		{
			log.error("Unhandled error in getInsuranceByProvider() | Search Obj: " + obj + " | Error: " + e.getMessage());
		}
		
		return rList;
	}
	
	public List<InsuranceProvider> getAllInsuranceProviders()
	{
		List<InsuranceProvider> rList = null;
		
		try
		{
			rList = new InsuranceProviderServiceImpl().getAllProviders(); // Might cause a problem idk lets find out :D
		}
		catch(NullPointerException e)
		{
			log.error("Object was null in getAllInsuranceProviders() | Error: " + e.getMessage());
		}
		catch(Exception e)
		{
			log.error("Unhandled error in getAllInsuranceProviders() | Error: " + e.getMessage());
		}
		
		return rList;
	}

}
