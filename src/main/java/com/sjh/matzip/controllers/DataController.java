package com.sjh.matzip.controllers;

import com.sjh.matzip.entities.data.PlaceEntity;
import com.sjh.matzip.entities.data.ReviewEntity;
import com.sjh.matzip.entities.data.ReviewImageEntity;
import com.sjh.matzip.entities.member.UserEntity;
import com.sjh.matzip.enums.data.AddReviewResult;
import com.sjh.matzip.execptions.RollbackException;
import com.sjh.matzip.services.DataService;
import com.sjh.matzip.vos.data.PlaceVo;
import com.sjh.matzip.vos.data.ReviewVo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController(value = "com.sjh.matzip.controllers")
@RequestMapping(value = "data")
public class DataController {
    private final DataService dataService;

    @Autowired
    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping(value = "place", produces = MediaType.APPLICATION_JSON_VALUE)
    public PlaceVo[] getPlace(@RequestParam(value = "minLat") double minLat,
                            @RequestParam(value = "minLng") double minLng,
                            @RequestParam(value = "maxLat") double maxLat,
                            @RequestParam(value = "maxLng") double maxLng) {
        return this.dataService.getPlaces(minLat, minLng, maxLat, maxLng);
    }

    @GetMapping(value = "placeImage")
    public ResponseEntity<byte[]> getPlaceImage(@RequestParam(value = "pi") int index) {
        ResponseEntity<byte[]> responseEntity;
        PlaceEntity place = this.dataService.getPlace(index);

        if (place == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf(place.getImageType()));
            headers.setContentLength(place.getImage().length);
            responseEntity = new ResponseEntity<>(place.getImage(), HttpStatus.OK);
        }
        return responseEntity;
    }
    @GetMapping(value = "review",produces = MediaType.APPLICATION_JSON_VALUE)
    public ReviewVo[] getReview(@RequestParam(value = "pi")int placeIndex){
       return this.dataService.getReviews(placeIndex);
    }

    @PostMapping(value = "review")
    @ResponseBody
    public String postReview(@SessionAttribute(value = "user", required = false) UserEntity user, @RequestParam(value = "images", required = false) MultipartFile[] images, ReviewEntity review) throws IOException, RollbackException {
        JSONObject responseObject = new JSONObject();
        Enum<?>result;
        try {
            result=this.dataService.addReview(user,review,images);
        }catch (RollbackException ignored){
            result= AddReviewResult.FAILURE;
        }
        responseObject.put("result",result.name().toLowerCase());
        return responseObject.toString();
    }
    @DeleteMapping(value = "review",produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteReview(){
        return null;
    }

    @GetMapping(value = "reviewImage")
    public ResponseEntity<byte[]> getReviewImage(@RequestParam(value = "index") int index) {
        ResponseEntity<byte[]> responseEntity;
        ReviewImageEntity reviewImage = this.dataService.getReviewImage(index);

        if (reviewImage == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf(reviewImage.getType()));
            headers.setContentLength(reviewImage.getData().length);
            responseEntity = new ResponseEntity<>(reviewImage.getData(), HttpStatus.OK);
        }
        return responseEntity;
    }

}
