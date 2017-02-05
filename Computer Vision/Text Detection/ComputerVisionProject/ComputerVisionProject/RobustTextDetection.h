#ifndef __RobustTextDetection__RobustTextDetection__
#define __RobustTextDetection__RobustTextDetection__

#pragma once

#include <iostream>
#include <bitset>
#include <numeric>
#include <opencv2/opencv.hpp>
#include "ConnectedComponent.h"

using namespace std;
using namespace cv;

double round(double number);

struct RobustTextParam 
{
    int minMSERArea;
    int maxMSERArea;
    int cannyThresh1;
    int cannyThresh2;
    
    int maxConnCompCount;
    int minConnCompArea;
    int maxConnCompArea;
    
    float minEccentricity;
    float maxEccentricity;
    float minSolidity;
    float maxStdDevMeanRatio;
};

void InitParam(RobustTextParam &P);

class RobustTextDetection 
{
private:
    string tempImageDirectory;
    RobustTextParam param;

public:
	RobustTextDetection() { InitParam(this->param);}
    RobustTextDetection(string temp_img_directory = "");
    RobustTextDetection(RobustTextParam& param, string temp_img_directory = "");
    pair<Mat, Rect> apply(Mat& image);
    
protected:
    Mat preprocessImage(Mat& image);
    Mat computeStrokeWidth(Mat& dist);
    Mat createMSERMask(Mat& grey);
    
    static int toBin(const float angle, const int neighbors = 8);
    Mat growEdges(Mat& image, Mat& edges);
    
    vector<Point> convertToCoords(int x, int y, bitset<8> neighbors);
    vector<Point> convertToCoords(Point& coord, bitset<8> neighbors);
    vector<Point> convertToCoords(Point& coord, uchar neighbors);
    bitset<8> getNeighborsLessThan(int * curr_ptr, int x, int * prev_ptr, int * next_ptr);
    
    Rect clamp(Rect& rect, Size size);
};

#endif
