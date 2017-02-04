function pyramid_all = pyr_sifthist( image )
%SIFT Summary of this function goes here
%   Detailed explanation goes here

    ndata_max = 100000; %use 4% avalible memory if its greater than the default
    
    %set param
    params.maxImageSize = 1000
    params.gridSpacing = 1
    params.patchSize = 16
    params.dictionarySize = 200
    params.numTextonImages = 300
    params.pyramidLevels = 2
    params.oldSift = false;
   
    binsHigh = 2^(params.pyramidLevels-1);
    %%

    if ndims(image) == 3
        image = im2double(rgb2gray(image));
    else
        image = im2double(image);
    end
    features = sp_gen_sift(image, params);
    
    %%
    
    data2add = features.data;
    if(size(data2add,1)>ndata_max/params.numTextonImages )
        p = randperm(size(data2add,1));
        data2add = data2add(p(1:floor(ndata_max/params.numTextonImages)),:);
    end
    sift = [data2add];
    %% perform clustering
    options = zeros(1,14);
    options(1) = 1; % display
    options(2) = 1;
    options(3) = 0.1; % precision
    options(5) = 1; % initialization
    options(14) = 100; % maximum iterations

    centers = zeros(params.dictionarySize, size(sift,2));

    %% run kmeans
    dictionary = sp_kmeans(centers, sift, options);

    ndata = size(features.data,1);
    %fprintf('Loaded %s, %d descriptors\n', inFName, ndata);

    %% find texton indices and compute histogram 
    
    H_all = [];
    texton_ind.data = zeros(ndata,1);
    texton_ind.x = features.x;
    texton_ind.y = features.y;
    texton_ind.wid = features.wid;
    texton_ind.hgt = features.hgt;
    %run in batches to keep the memory foot print small
    batchSize = 100000;
    if ndata <= batchSize
        dist_mat = sp_dist2(features.data, dictionary);
        [min_dist, min_ind] = min(dist_mat, [], 2);
        texton_ind.data = min_ind;
    else
        for j = 1:batchSize:ndata
            lo = j;
            hi = min(j+batchSize-1,ndata);
            dist_mat = sp_dist2(features.data(lo:hi,:), dictionary);
            [min_dist, min_ind] = min(dist_mat, [], 2);
            texton_ind.data(lo:hi,:) = min_ind;
        end
    end

    H = hist(texton_ind.data, 1:params.dictionarySize);
    H_all = [H_all; H];
    
    pyramid_all = zeros(1,params.dictionarySize*sum((2.^(0:(params.pyramidLevels-1))).^2));
    
    %% get width and height of input image
    wid = texton_ind.wid;
    hgt = texton_ind.hgt;
    
    %% compute histogram at the finest level
    pyramid_cell = cell(params.pyramidLevels,1);
    pyramid_cell{1} = zeros(binsHigh, binsHigh, params.dictionarySize);

    for i=1:binsHigh
        for j=1:binsHigh

            % find the coordinates of the current bin
            x_lo = floor(wid/binsHigh * (i-1));
            x_hi = floor(wid/binsHigh * i);
            y_lo = floor(hgt/binsHigh * (j-1));
            y_hi = floor(hgt/binsHigh * j);
            
            texton_patch = texton_ind.data( (texton_ind.x > x_lo) & (texton_ind.x <= x_hi) & ...
                                            (texton_ind.y > y_lo) & (texton_ind.y <= y_hi));
            
            % make histogram of features in bin
            pyramid_cell{1}(i,j,:) = hist(texton_patch, 1:params.dictionarySize)./length(texton_ind.data);
        end
    end

    %% compute histograms at the coarser levels
    num_bins = binsHigh/2;
    for l = 2:params.pyramidLevels
        pyramid_cell{l} = zeros(num_bins, num_bins, params.dictionarySize);
        for i=1:num_bins
            for j=1:num_bins
                pyramid_cell{l}(i,j,:) = ...
                pyramid_cell{l-1}(2*i-1,2*j-1,:) + pyramid_cell{l-1}(2*i,2*j-1,:) + ...
                pyramid_cell{l-1}(2*i-1,2*j,:) + pyramid_cell{l-1}(2*i,2*j,:);
            end
        end
        num_bins = num_bins/2;
    end

    %% stack all the histograms with appropriate weights
    pyramid = [];
    for l = 1:params.pyramidLevels-1
        pyramid = [pyramid pyramid_cell{l}(:)' .* 2^(-l)];
    end
    pyramid = [pyramid pyramid_cell{params.pyramidLevels}(:)' .* 2^(1-params.pyramidLevels)];

    pyramid_all(1,:) = pyramid;
end