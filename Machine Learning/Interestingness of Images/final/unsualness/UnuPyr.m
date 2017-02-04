function pyr_score = UnuPyr( image )
%UNUPYR Summary of this function goes here
%   Detailed explanation goes here
    disp('SIFT image size');
    disp(size(image));
    tpyr = pyr_sifthist(image);
    pyr_score = LOF(tpyr, 10);
end