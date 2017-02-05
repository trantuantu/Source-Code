#ifndef __RobustTextDetection__ConnectedComponent__
#define __RobustTextDetection__ConnectedComponent__

#pragma once

#include <iostream>
#include <functional>
#include <stdexcept>
#include <opencv2/opencv.hpp>

using namespace std;
using namespace cv;

/**
 * Structure that describes the property of the connected component
 */
struct ComponentProperty 
{
    int labelID;
    int area;
    float eccentricity;
    float solidity;
    Point2f centroid;
};

class ConnectedComponent 
{
private:
    int connectivityType;
    int maxComponent;
    int nextLabel;
    vector<ComponentProperty> properties;

public:
    ConnectedComponent(int max_component = 1000, int connectivity_type = 8);
    virtual ~ConnectedComponent();
    
    Mat apply(const Mat& image);
    
    int getComponentsCount();
    const vector<ComponentProperty>& getComponentsProperties();
    
    vector<int> get8Neighbors(int * curr_ptr, int * prev_ptr, int x);
    vector<int> get4Neighbors(int * curr_ptr, int * prev_ptr, int x);
    
protected:
    float calculateBlobEccentricity(const Moments& moment);
    Point2f calculateBlobCentroid(const Moments& moment);
    
    void disjointUnion(int a, int b, vector<int>& parent);
    int disjointFind(int a, vector<int>& parent, vector<int>& labels);
};

#endif 
