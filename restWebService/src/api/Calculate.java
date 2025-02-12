package api;

import javax.ws.rs.*;

@Path("/calculate")

public class Calculate {
    @Path("/sum")
    @GET
    @Produces("text/plain")
    public String sum(@QueryParam("a") String a, @QueryParam("b") String b ){
        System.out.println("Sum is calculating....");
        int result = Integer.parseInt(a) + Integer.parseInt(b);
        return String.valueOf(result);
    }
    @Path("/minus")
    @GET
    @Produces("text/plain")
    public String minus(@QueryParam("a") String a, @QueryParam("b") String b){
        System.out.println("Minus is calculating....");
        int result = Integer.parseInt(a) - Integer.parseInt(b);
        return String.valueOf(result);
    }
}
