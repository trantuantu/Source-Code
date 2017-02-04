function [sift_result, gist_result] = generalPref2( test_path)
%GENERALPREF Summary of this function goes here
%   Detailed explanation goes here

% Scene recognition
%
% This script trains a SVM to classify 8 scene categories.

addpath(genpath('./'))
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Parameters
% Scene recognition
%
% This script trains a SVM to classify 8 scene categories.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Parameters
HOMEIMAGES_TRAIN = 'generalPref\data\train'; % each category has to be in a separate folder
HOMEIMAGES_TEST = strcat('dataset\', test_path); % each category has to be in a separate folder

%HOMEIMAGES = {HOMEIMAGES_TRAIN; HOMEIMAGES_TEST};
% build index database
%D = LMdatabase(HOMEIMAGES, HOMEIMAGES);
Dtrain = LMdatabase(HOMEIMAGES_TRAIN, HOMEIMAGES_TRAIN); % build index
Dtest = LMdatabase(HOMEIMAGES_TEST, HOMEIMAGES_TEST); % build index
D = [Dtrain, Dtest];

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% PART 1:  Compute global descriptors.
%%gist Parameters:
clear GISTparama
GISTparam.imageSize = [256 256]; % it works also with non-square images
GISTparam.orientationsPerScale = [8 8 8 8];
GISTparam.numberBlocks = 4;
GISTparam.fc_prefilt = 4;
% compute gist for all images in the database
[gist_train, GISTparam] = LMgist(Dtrain, HOMEIMAGES_TRAIN, GISTparam);
[gist_test, GISTparam] = LMgist(Dtest, HOMEIMAGES_TEST, GISTparam);

% 
%--------------------------------------------------------------------------------------------------
% % SIFT visual words
clear VWparamsift
VWparamsift.imageSize = [256 256]; % it works also with non-square images
VWparamsift.grid_spacing = 1; % distance between grid centers
VWparamsift.patch_size = 16; % size of patch from which to compute SIFT descriptor (it has to be a factor of 4)
VWparamsift.NumVisualWords = 200; % number of visual words
VWparamsift.Mw = 2; % number of spatial scales for spatial pyramid histogram
VWparamsift.descriptor = 'sift';
VWparamsift.w = VWparamsift.patch_size/2; % boundary for SIFT

% Build dictionary of visual words
VWparamsift = LMkmeansVisualWords(D(1:10:end), HOMEIMAGES_TRAIN, HOMEIMAGES_TEST, VWparamsift);
% Compute visual words:
[VWsift, sptHistsift] = LMdenseVisualWords(D, HOMEIMAGES_TRAIN, HOMEIMAGES_TEST, VWparamsift);
sptHistsift = sptHistsift';
sptHistsift_train = sptHistsift(1:600,:);
sptHistsift_test = sptHistsift(601:size(sptHistsift, 1),:);
%---------------------------------------------------------------------------------------- 
% % HOG visual words
% % clear VWparamhog
% % VWparamhog.imageSize = [256 256]; % it works also with non-square images
% % VWparamhog.grid_spacing = 1; % distance between grid centers
% %VWparamhog.patch_size = 16; % size of patch from which to compute SIFT descriptor (it has to be a factor of 4)
% % VWparamhog.NumVisualWords = 200; % number of visual words
% % VWparamhog.Mw = 2; % number of spatial scales for spatial pyramid histogram
% % VWparamhog.descriptor = 'hog';
% % VWparamhog.w = floor(VWparamhog.patch_size/2*2.5); % boundary for HOG
% % 
% % % Build dictionary of visual words
% % VWparamhog = LMkmeansVisualWords(D(1:10:end), HOMEIMAGES, VWparamhog);
% % 
% % % Compute visual words:
% % [VWhog, sptHisthog] = LMdenseVisualWords(D, HOMEIMAGES, VWparamhog);
% % sptHisthog = sptHisthog';

%-----------------------------------------------------------------------------------------
% data_train = zeros(600, 7000);
% for idx = 1:600
%     load trees
%     rgb = LMimread(Dtrain, idx, HOMEIMAGES_TRAIN);
%     rgb = imresize(rgb, [128, 128]);
%     %BW = im2bw(rgb, 0.5);
%     rgb1 = reshape(rgb, [1, 16384]);
%     data_train(idx, :) = datasample(rgb1, 7000);
% end
% 
% data_test = zeros(3, 7000);
% for idx = 1:length(Dtest)
%     load trees
%     rgb = LMimread(Dtest, idx, HOMEIMAGES_TEST);
%     rgb = imresize(rgb, [128, 128]);
%     rgb = rgb2gray(rgb);
%     %BW = im2bw(rgb, 0.5);
%     rgb1 = reshape(rgb, [1, 16384]);
%     data_test(idx, :) = datasample(rgb1, 7000);
% end
%--------------------------------------------------------------------------------------------
%Color Histograms
% data_train = zeros(150, 10);
% for idx = 1:150
%     load trees
%     rgb = LMimread(Dtrain, idx, HOMEIMAGES_TRAIN);
%     rgb = imresize(rgb, [256, 256]);
%     rgb = reshape(rgb, [1, 65536]);
%     colorHist = hist(double(rgb));
%     rgb1 = reshape(colorHist, [1, 10]);
%     data_train(idx, :) = rgb1;
% end
% 
% data_test = zeros(3, 10);
% for idx = 1:length(Dtest)
%     load trees
%     rgb = LMimread(Dtest, idx, HOMEIMAGES_TEST);
%     rgb = imresize(rgb, [256, 256]);
%     rgb = rgb2gray(rgb);
%     rgb = reshape(rgb, [1, 65536]);
%     colorHist = hist(double(rgb));
%     rgb1 = reshape(colorHist, [1, 10]);
%     data_test(idx, :) = rgb1;
% end

%--------------------------------------------------------------------------------------------


%data = data(:, 1:512)
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% PART 2: Scene recognition
% get classes



[class_training, categories_train] = folder2class(Dtrain);
[class_test, categories_test] = folder2class(Dtest);

%disp(size(class))
% split training/validation/test
%[jtraining, jtest, jval] = splitTrainingTest(class, 9, 9, 1);
%class_training = class(jtraining);
%class_test = class(jtest);
descriptors = {'gist', 'sift'};

for m = 1:length(descriptors)
    % svm parameters
    lambda = 0.2;
    opt.iter_max_Newton = 200;
    opt.cg = 1;
    
    % building kernel
    global K
    switch descriptors{m}
        case 'gist'
            svm.type = 'rbf';
            svm.sig = .6;
            
            % select gist
            Ftraining = gist_train;
            Ftest = gist_test;
        case 'sift'
            
            % building kernel
            svm.type = 'histintersection';
            
            % select gist
            Ftraining = sptHistsift_train;
            Ftest = sptHistsift_test;
            
    end
    
    % train and test
    K = kernel(Ftraining, Ftraining, svm);
    Kt = kernel(Ftest, Ftraining, svm);
    [dg, n] = sort(K, 'descend');
    %figure
    %for i = 1:16
    %    img = LMimread(Dtrain, n(i), HOMEIMAGES_TRAIN);
    %    subplot(4,4,i)
    %    imshow(img)
    %end
    score_test = zeros(length(class_test), length(categories_train));
    figure
    for c = 1:length(categories_train)
        % train
        Y = 2*(class_training' == c)-1;
        [beta,b]=primal_svm(0, Y, lambda, opt);
        % test
        score_test(:,c) = Kt*beta+b;
    end
    % evaluation multi-class
    [s,class_hat] = max(score_test, [], 2);
    C = confusionMatrix(class_test, class_hat', 'A', 'B');
    subplot(121); title(descriptors{m})
    perf = mean(diag(C));
    if m == 1
        gist_hat = class_hat;
    else if m == 2
        sift_hat = class_hat;
        end
    end
end
%The result of interesting of an image was calculated as the fraction of selections over
%views. Fig 2c 


for idx = 1 : length(gist_hat)
    if (gist_hat(idx) == 1)
        gist_result(idx) = 0.68
    elseif (gist_hat(idx) == 2)
        gist_result(idx) = 0.52
    elseif (gist_hat(idx) == 3) 
        gist_result(idx) = 0.18    
    elseif (gist_hat(idx) == 4) 
        gist_result(idx) = 0.34
    elseif (gist_hat(idx) == 5) 
        gist_result(idx) = 0.69
    elseif (gist_hat(idx) == 6)
        gist_result(idx) = 0.12
    elseif (gist_hat(idx) == 7) 
        gist_result(idx) = 0.68
    elseif (gist_hat(idx) == 8)
        gist_result(idx) = 0.34
    elseif (gist_hat(idx) == 9)
        gist_result(idx) = 0.28
    elseif (gist_hat(idx) == 10)
        gist_result(idx) = 0.52
    elseif (gist_hat(idx) == 11)
        gist_result(idx) = 0.13
    elseif (gist_hat(idx) == 12)
        gist_result(idx) = 0.23
    elseif (gist_hat(idx) == 13)
        gist_result(idx) = 0.22
    elseif (gist_hat(idx) == 14)
        gist_result(idx) = 0.35
    else
        gist_result(idx) = 0.28
    end
end


for idx = 1 : length(sift_hat)
    if (sift_hat(idx) == 1)
        sift_result(idx) = 0.68
    elseif (sift_hat(idx) == 2)
        sift_result(idx) = 0.52
    elseif (sift_hat(idx) == 3) 
        sift_result(idx) = 0.18    
    elseif (sift_hat(idx) == 4) 
        sift_result(idx) = 0.34
    elseif (sift_hat(idx) == 5) 
        sift_result(idx) = 0.69
    elseif (sift_hat(idx) == 6)
        sift_result(idx) = 0.12
    elseif (sift_hat(idx) == 7) 
        sift_result(idx) = 0.68
    elseif (sift_hat(idx) == 8)
        sift_result(idx) = 0.34
    elseif (sift_hat(idx) == 9)
        sift_result(idx) = 0.28
    elseif (sift_hat(idx) == 10)
        sift_result(idx) = 0.52
    elseif (sift_hat(idx) == 11)
        sift_result(idx) = 0.13
    elseif (sift_hat(idx) == 12)
        sift_result(idx) = 0.23
    elseif (sift_hat(idx) == 13)
        sift_result(idx) = 0.22
    elseif (sift_hat(idx) == 14)
        sift_result(idx) = 0.35
    else
        sift_result(idx) = 0.28
    end
end
end