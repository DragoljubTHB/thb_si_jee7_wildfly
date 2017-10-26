package thb.si;

import javax.json.Json;
import javax.json.JsonArray;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

@Path("Mandelbrot")
public class MandelbrotRESTEndpoint {
    @GET
    @Path("getMandelbrot")
    @Produces({"image/png"})
    public Response getMandelbrot(@QueryParam("w") Integer w,
                                  @QueryParam("h") Integer h,
                                  @QueryParam("it") Integer it){
        return Response.ok().entity((StreamingOutput) output -> {
            //output.write(repo.create());
            Mandelbrot mandelbrot = new Mandelbrot();
            mandelbrot.create(w, h, it);
            output.write(mandelbrot.getContentBytes());
            output.flush();
        }).build();
    }
    @GET
    @Path("generateToS3")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray generateToS3(@QueryParam("bucketname") String aBucketName) {

        S3Ctrl s3 = new S3Ctrl();
        Mandelbrot mandelbrot = new Mandelbrot();
        mandelbrot.create(800, 600, 1000);
        String uploadedFileName = s3.upload(aBucketName, mandelbrot.getContentBytes());

        return Json.createArrayBuilder()
                .add(aBucketName)
                .add(uploadedFileName)
                .add(Response.Status.OK.getStatusCode())
                .build();
    }

    @DELETE
    @Path("deleteFromS3")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray deleteFromS3(@QueryParam("bucketname") String aBucketName) {
        S3Ctrl s3 = new S3Ctrl();
        s3.deleteAllFilesFrom(aBucketName);

        return Json.createArrayBuilder()
                .add(aBucketName)
                .add(Response.Status.OK.getStatusCode())
                .build();
    }



    @GET
    @Path("c")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray getOne(@QueryParam("w") Integer w,
                            @QueryParam("h") Integer h,
                            @QueryParam("it") Integer it){
        return Json.createArrayBuilder()
                .add(w)
                .add(h)
                .add(it)
                .build();
    }
}
