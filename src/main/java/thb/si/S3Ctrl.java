package thb.si;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class S3Ctrl {

    private AmazonS3 s3Client;

    public S3Ctrl() {
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.US_WEST_2)
                .build();
    }

    public String upload(String bucketName, byte[] content) {

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(content.length);
        metadata.setContentType("image/png");
        String fileName = LocalDateTime.now().toString() + ".png";
        s3Client.putObject(new PutObjectRequest(
                bucketName,
                fileName,
                new ByteArrayInputStream(content),
                metadata));

        return fileName;
    }

    public void deleteAllFilesFrom(String aBucketName) {
        List<DeleteObjectsRequest.KeyVersion> keys = new ArrayList<>();
        s3Client.listObjects(aBucketName)
                .getObjectSummaries()
                .forEach(o -> {
                    keys.add(new DeleteObjectsRequest.KeyVersion(o.getKey()));
                });
        DeleteObjectsResult result = s3Client.deleteObjects(
                new DeleteObjectsRequest(aBucketName).withKeys(keys));
    }
}
