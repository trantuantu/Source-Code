function compose = compose( image )
%COMPOSE Summary of this function goes here
%   Detailed explanation goes here

addpath('GCMex');
addpath('SLIC');
[label, adjMatrix, C] = slic(image, 3000, 10, 1, 'median');


% Calc the Euclidean distance in the descriptor space of a superpixel i 
% to the nearest-neighboring superpixel
% SIFT, Texton, Color Histogram
end