function [ gist ] = gistdescriptor(img)
%GISTDESCRIPTOR Summary of this function goes here
%   Detailed explanation goes here

clear param
param.imageSize = [256 256];
param.orientationsPerScale = [8 8 8 8];
param.numberBlocks = 4;
param.fc_prefilt = 4;

[gist, ~] = LMgist(img, '', param);

end

