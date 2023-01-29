package com.fu.server.mapper.my;

import com.fu.server.domain.Course;
import com.fu.server.dto.CourseDto;
import com.fu.server.dto.CoursePageDto;
import com.fu.server.dto.SortDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyCourseMapper {
    List<CourseDto> list(@Param("pageDto") CoursePageDto pageDto);

    int updateTime(@Param("courseId") String courseId);

    int updateSort(SortDto sortDto);

    int moveSortsBackward(SortDto sortDto);

    int moveSortsForward(SortDto sortDto);

    int moveSortsByDelete(Course course);
}
