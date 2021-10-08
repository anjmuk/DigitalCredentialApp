package com.digitalcredentials.vo;

import com.amazon.ion.IonList;
import com.amazon.ion.IonStruct;
//Course info
public class CourseListVO {
	 IonList addedCourseList;
	 IonStruct courseStructure;
	 
	public IonStruct getCourseStructure() {
		return courseStructure;
	}

	public void setCourseStructure(IonStruct courseStructure) {
		this.courseStructure = courseStructure;
	}

	public IonList getAddedCourseList() {
		return addedCourseList;
	}

	public void setAddedCourseList(IonList addedCourseList) {
		this.addedCourseList = addedCourseList;
	}
	 
}
