function result = generalPref( test_path)
%GENERALPREF Summary of this function goes here
%   Detailed explanation goes here

% Scene recognition
%
% This script trains a SVM to classify 8 scene categories.

addpath(genpath('./'))

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Parameters

 HOMEIMAGES_TRAIN = 'generalPref\data\train'; % each category has to be in a separate folder
 HOMEIMAGES_TEST = strcat('dataset\', test_path); % each category has to be in a separate folder
% build index database


Dtrain = LMdatabase(HOMEIMAGES_TRAIN, HOMEIMAGES_TRAIN); % build index
Dtest = LMdatabase(HOMEIMAGES_TEST, HOMEIMAGES_TEST); % build index

%Dtrain = Dtrain(1:4:end); % for speed, we will reduce the number of images. But you can remove this line.

%disp(size(Dtrain))

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
% clear VWparamsift
% VWparamsift_train.imageSize = [256 256]; % it works also with non-square images
% VWparamsift_train.grid_spacing = 1; % distance between grid centers
% VWparamsift_train.patch_size = 16; % size of patch from which to compute SIFT descriptor (it has to be a factor of 4)
% VWparamsift_train.NumVisualWords = 200; % number of visual words
% VWparamsift_train.Mw = 2; % number of spatial scales for spatial pyramid histogram
% VWparamsift_train.descriptor = 'sift';
% VWparamsift_train.w = VWparamsift_train.patch_size/2; % boundary for SIFT

% Build dictionary of visual words
% VWparamsift_train = LMkmeansVisualWords(Dtrain, HOMEIMAGES_TRAIN, VWparamsift_train);
% [VWsift1, sptHistsift_train] = LMdenseVisualWords(Dtrain, HOMEIMAGES_TRAIN, VWparamsift_train);
% 
% % % Compute visual words:
% VWparamsift_test.imageSize = [256 256]; % it works also with non-square images
% VWparamsift_test.grid_spacing = 1; % distance between grid centers
% VWparamsift_test.patch_size = 16; % size of patch from which to compute SIFT descriptor (it has to be a factor of 4)
% VWparamsift_test.NumVisualWords = 200; % number of visual words
% VWparamsift_test.Mw = 2; % number of spatial scales for spatial pyramid histogram
% VWparamsift_test.descriptor = 'sift';
% VWparamsift_test.w = VWparamsift_test.patch_size/2; % boundary for SIFT
% %VWparamsift_train = LMkmeansVisualWords(Dtest, HOMEIMAGES_TEST, VWparamsift_train);
% [VWsift2, sptHistsift_test] = LMdenseVisualWords(Dtest, HOMEIMAGES_TEST, VWparamsift_test);
% sptHistsift_train = sptHistsift_train';
% sptHistsift_test = sptHistsift_test';

% 
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
descriptors = {'gist'};

for m = 1:length(descriptors)
    % svm parameters
    lambda = 0.2;
    opt.iter_max_Newton = 200;
    opt.cg = 1;
    
    % building kerne
    global K
    switch descriptors{m}
        case 'gist'
            svm.type = 'rbf';
            svm.sig = .6;
            
            % select gist
            Ftraining = gist_train;
            % Ftest = gist(image);
            Ftest = gist_test;

%         case 'sift'
%             
%             % building kernel
%             svm.type = 'histintersection';
%             
%             % select gist
%             Ftraining = sptHistsift_train;
%             Ftest = sptHistsift_test;
            
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
end

%The result of interesting of an image was calculated as the fraction of selections over
%views. Fig 2c 
result = []

for idx = 1 : length(class_hat)
    if (class_hat(idx) == 1)
        result(idx) = 0.68
    elseif (class_hat(idx) == 2)
        result(idx) = 0.52 
    elseif (class_hat(idx) == 3) 
        result(idx) = 0.18    
    elseif (class_hat(idx) == 4) 
        result(idx) = 0.34
    elseif (class_hat(idx) == 5) 
        result(idx) = 0.69
    elseif (class_hat(idx) == 6)
        result(idx) = 0.12
    elseif (class_hat(idx) == 7) 
        result(idx) = 0.68
    elseif (class_hat(idx) == 8)
        result(idx) = 0.34
    elseif (class_hat(idx) == 9)
        result(idx) = 0.28
    elseif (class_hat(idx) == 10)
        result(idx) = 0.52
    elseif (class_hat(idx) == 11)
        result(idx) = 0.13
    elseif (class_hat(idx) == 12)
        result(idx) = 0.23
    elseif (class_hat(idx) == 13)
        result(idx) = 0.22
    elseif (class_hat(idx) == 14)
        result(idx) = 0.35
    else
        result(idx) = 0.28
    end
end

end