addpath('RawRGB')
addpath('image')
addpath('GIST')
addpath('SIFT')


im = imread('best.png')

[pixel, gist, pyr] = Unusualness(im);