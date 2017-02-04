function pixel_score = UnuPixel( image )
%UNUPIXEL Summary of this function goes here
%   Detailed explanation goes here
    disp('Pixel image size');
    disp(size(image));
    im = RGB(image);
    pixel_score = LOF(im, 10);
    
    % gist_score = LOF(tgist, 10);
    % result = LocalOutlierFactor(dataset, params);
end