package se.hallonapl.veggietales;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import se.hallonapl.veggietales.model.*;
import se.hallonapl.veggietales.persistence.IPersistenceService;
import se.hallonapl.veggietales.persistence.PersistenceServiceProvider;

@Path("/vegetables")
public class VegetablesService {

	@GET
	@Produces(MediaType.TEXT_XML)
	public Response getAllVegetables() {
		List<Vegetable> vgts;

		try {
			IPersistenceService psvc = PersistenceServiceProvider.getPersistenceService();
			vgts = psvc.retrieveAllVegetables();
		}
		catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}
		
		StringBuilder xmlstrb = new StringBuilder();
		xmlstrb.append("<Vegetables>");
		vgts.stream().map((Vegetable v) -> (v.toXML())).forEach((String s) -> xmlstrb.append(s));
		xmlstrb.append("</Vegetables>");
		
		return Response.status(Status.OK).entity(xmlstrb.toString()).build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.TEXT_XML)
	public Response getVegetableById(@PathParam("id") String idString) {
		Vegetable vgt;
		try {
			int id = Integer.parseInt(idString);
			IPersistenceService psvc = PersistenceServiceProvider.getPersistenceService();
			vgt = psvc.retrieveVegetable(id);
		}
		catch (NumberFormatException ne) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}
		if (vgt != null) {
			return Response.status(Status.OK).entity(vgt.toXML()).build();	
		}
		else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@PUT
	@Produces(MediaType.TEXT_XML)
	public Response addVegetable(@QueryParam("price") String priceString, @QueryParam("name") String name ) {
		int newId;
		IPersistenceService psvc;
		
		try {
			if (priceString != null && name != null) {
				double price = Double.parseDouble(priceString);
				psvc = PersistenceServiceProvider.getPersistenceService();
				newId = psvc.createVegetable(price, name);
			}
			else {
				return Response.status(Status.BAD_REQUEST).build();
			}
		}
		catch (NumberFormatException ne) {
			return Response.status(Status.BAD_REQUEST).build();			
		}
		catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}
		if (newId > 0) {			
			return Response.status(Status.CREATED).entity("<NewID>" + newId + "<NewID>").build();	
		}
		else {
			return Response.status(Status.NO_CONTENT).build(); //the insert did not return an ID; in this case we might actually have serious issues
		}
	}

	@DELETE
	@Path("/{id}")
	public Response deleteVegetable(@PathParam("id") String idString) {
		try {
			int id = Integer.parseInt(idString);
			IPersistenceService psvc = PersistenceServiceProvider.getPersistenceService();
			if (psvc.tryDeleteVegetable(id)) {
				return Response.status(Status.OK).build();	
			}
			else {
				return Response.status(Status.NOT_FOUND).build();
			}		
		}
		catch (NumberFormatException ne) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}
	}

	@POST
	@Path("/{id}")
	public Response updateVegetable(@PathParam("id") String idString, @QueryParam("price") String newPriceString, @QueryParam("name") String newName) {
		try {
			if (newPriceString != null && newName != null) {
				int id = Integer.parseInt(idString);
				double newPrice = Double.parseDouble(newPriceString);
				IPersistenceService psvc = PersistenceServiceProvider.getPersistenceService();
				if (psvc.tryUpdateVegetable(id, newPrice, newName)) {
					return Response.status(Status.OK).build();	
				}
				else {
					return Response.status(Status.NOT_FOUND).build();
				}
			}
			else {
				return Response.status(Status.BAD_REQUEST).build();
			}
		}
		catch (NumberFormatException ne) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}
	}

}
