package com.sjh.matzip.services;

import com.sjh.matzip.entities.data.PlaceEntity;
import com.sjh.matzip.entities.data.ReviewEntity;
import com.sjh.matzip.entities.data.ReviewImageEntity;
import com.sjh.matzip.entities.member.UserEntity;
import com.sjh.matzip.enums.data.AddReviewResult;
import com.sjh.matzip.execptions.RollbackException;
import com.sjh.matzip.interfaces.IResult;
import com.sjh.matzip.mappers.IDataMapper;
import com.sjh.matzip.vos.data.PlaceVo;
import com.sjh.matzip.vos.data.ReviewVo;
import jdk.jfr.TransitionFrom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

@Service(value = "com.sjh.matzip.services.DataService")
public class DataService {
    private final IDataMapper dataMapper;

    @Autowired
    public DataService(IDataMapper dataMapper) {
        this.dataMapper = dataMapper;
    }
    public PlaceEntity getPlace(int index){
        return this.dataMapper.selectPlaceByIndex(index);
    }

    public PlaceVo[] getPlaces(double minLat, double minLng , double maxLat, double maxLng){
        return this.dataMapper.selectPlacesExceptImage(minLat, minLng, maxLat, maxLng);

    }
    @Transactional
    public Enum<? extends IResult> addReview(UserEntity user, ReviewEntity review, MultipartFile[] images) throws RollbackException, IOException {
        if(user==null){
            return AddReviewResult.NOT_SIGNED;
        }
        review.setUserId(user.getId());
        if(this.dataMapper.insertReview(review)==0){
            return AddReviewResult.FAILURE;
        }
        if(images!=null && images.length>0){
            for(MultipartFile image : images){
                ReviewImageEntity reviewImage=new ReviewImageEntity();
                reviewImage.setReviewIndex(review.getIndex());
                reviewImage.setData(image.getBytes());
                reviewImage.setType(image.getContentType());
                if(this.dataMapper.insertReviewImage(reviewImage)==0){
                    throw new RollbackException();

                }
            }
        }
        return AddReviewResult.SUCCESS;
    }
    public ReviewVo[] getReviews(int placeIndex){
        ReviewVo[] reviews=this.dataMapper.selectReviewsByPlaceIndex(placeIndex);
        for(ReviewVo review : reviews){
            ReviewImageEntity[] reviewImages=this.dataMapper.selectReviewImageByReviewIndexExceptData(review.getIndex());
            int[] reviewImageIndexes = Arrays.stream(reviewImages).mapToInt(ReviewImageEntity::getIndex).toArray();
            review.setImageIndexes(reviewImageIndexes);
        }
        return reviews;
    }
    public ReviewImageEntity getReviewImage(int index){
        return this.dataMapper.selectReviewImageByIndex(index);
    }
}
