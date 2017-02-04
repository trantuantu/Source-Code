function rawRGB = RGB( image )
%RGB Summary of this function goes here
%   Detailed explanation goes here
    image = imresize(image,[32, 32]);
    tmp = size(image);    
    sz = 1;
    for i = 1:numel(tmp)
        sz = sz*tmp(i);
    end
    rawRGB = reshape(image, [1, sz]);
end