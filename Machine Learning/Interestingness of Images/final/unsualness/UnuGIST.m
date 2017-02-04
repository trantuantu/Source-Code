function gist_score = UnuGIST(image)
    disp('GIST image size:');
    disp(size(image));
    %UNUGIST Summary of this function goes here
    %   Detailed explanation goes here
    % Parameters:
    
    tgist = gist(image);
    gist_score = LOF(tgist, 10);
    % result = LocalOutlierFactor(dataset, params);
end