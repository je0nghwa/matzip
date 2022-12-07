package com.sjh.matzip.mappers;

import com.sjh.matzip.entities.data.PlaceEntity;
import com.sjh.matzip.entities.data.ReviewEntity;
import com.sjh.matzip.entities.data.ReviewImageEntity;
import com.sjh.matzip.vos.data.PlaceVo;
import com.sjh.matzip.vos.data.ReviewVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

@Mapper
public interface IDataMapper {
    int insertReview(ReviewEntity review);

    int insertReviewImage(ReviewImageEntity reviewImage);
    PlaceVo[] selectPlacesExceptImage(@Param(value = "minLat")double minLat,
                                    @Param(value = "minLng")double minLng,
                                    @Param(value = "maxLat")double maxLat,
                                    @Param(value = "maxLng")double maxLng);

    PlaceEntity selectPlaceByIndex(@Param(value = "index")int index);

    ReviewVo[] selectReviewsByPlaceIndex(@Param(value="placeIndex")int placeIndex);

    ReviewImageEntity selectReviewImageByIndex(@Param(value = "index")int index);

    ReviewImageEntity[] selectReviewImageByReviewIndexExceptData(@Param(value = "reviewIndex")int reviewIndex);


}
