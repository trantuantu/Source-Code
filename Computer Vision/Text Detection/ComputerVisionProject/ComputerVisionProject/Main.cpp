#pragma once

#include "stdafx.h"
#include <tesseract/baseapi.h>
#include "RobustTextDetection.h"

int _tmain(int argc, _TCHAR* argv[])
{
	/* Load input image */
	Mat image = imread("D:\\Test01.jpg");
    
	/* Set up parameters*/
    RobustTextParam param;
	InitParam(param);
    
    /* Apply Robust Text Detection */
    /* Write temp image files*/
    string temp_output_path = "D:\\";
    RobustTextDetection detector(param, temp_output_path);
    pair<Mat, Rect> result = detector.apply(image);
    
    /* Get the region where the candidate text is */
    Mat stroke_width( result.second.height, result.second.width, CV_8UC1, Scalar(0) );
    Mat(result.first, result.second).copyTo( stroke_width);
    
    /* Use Tesseract to try to decipher input image */
    tesseract::TessBaseAPI tesseract_api;
    tesseract_api.Init(NULL, "eng");
    tesseract_api.SetImage((uchar*) stroke_width.data, stroke_width.cols, stroke_width.rows, 1, stroke_width.cols);
    
    string out = string(tesseract_api.GetUTF8Text());

    /* Split the string */
    vector<string> splitted;
    istringstream iss(out);
    copy(istream_iterator<string>(iss), istream_iterator<string>(), back_inserter(splitted));
    
    /*Write detected text into console*/
    for(string &line: splitted) 
		cout << line << endl;
    
    rectangle(image, result.second, Scalar(0, 0, 255), 2);
    
    /* Append the original and stroke width images together */
    cvtColor( stroke_width, stroke_width, CV_GRAY2BGR );
    Mat appended( image.rows, image.cols + stroke_width.cols, CV_8UC3 );
    image.copyTo( Mat(appended, Rect(0, 0, image.cols, image.rows)));
    stroke_width.copyTo( Mat(appended, Rect(image.cols, 0, stroke_width.cols, stroke_width.rows)));
    
	/* Resize the window showing image with detected area and edeged enhanced mser */
	resize(appended, appended, Size(), 0.5f, 0.5f);

	/* Show the results*/
    imshow("", appended);
    waitKey();
    
    return 0;
}

