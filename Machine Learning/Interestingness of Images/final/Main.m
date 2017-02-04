
addpath(genpath('./'))

train_folder = 'dataset/train';
test_folder = 'dataset/test';


num_features = 5;
num_epoch = 10;

% general data
train_X = [];
test_X = [];

W = ones(num_features + 1, 1);

% only get image type png, jpg
image_train = dir(fullfile(train_folder, '*.jpg'));
image_test = dir(fullfile(test_folder, '*.jpg'));


[s_sift_pref, s_gist_pref] = generalPref2('train');

for i = 1: length(image_train)
    fprintf('Calc features for image %d\n', i);
    filename = strcat(train_folder,'/',image_train(i).name);
    image = imread(filename);        
    [s_pixel, s_gist, s_pyr] = Unusualness(image);
    s = [s_pixel, s_gist, s_pyr, s_sift_pref(i), s_gist_pref(i)];
    train_X = [train_X; s];
end

mi = (1 + sum(train_X, 1))./length(image_train);% pass error zero
train_X = 1./ (1 + exp(train_X./repmat(mi, length(image_train), 1)));

train_X = [repmat([1], length(image_train),1) train_X];


load('dataset/train/label.mat');

% add phan cua tu vao nay


train_Y = label;

num_images_train = length(image_train);
% start train
for i_train = 1:num_epoch
    for i = 1:length(image_train)        
        fprintf('Tranning image %d on epoch %d\n', i, i_train);
        out = W'*train_X(i, :)' - train_Y(i);
        grad = out.*reshape(train_X(i, :), num_features + 1, 1);
        W = W - grad/num_images_train;
    end
end

[s_sift_pref, s_gist_pref] = generalPref2('test');

ret = [];
for i = 1:length(image_test)
    fprintf('Test image calc interestingness %d\n', i);
    filename = strcat(test_folder, '/', image_test(i).name);
    image = imread(filename);   
    [s_pixel, s_gist, s_pyr] = Unusualness(image);
    s = [s_pixel, s_gist, s_pyr, s_sift_pref(i), s_gist_pref(i)];
    test_X = [test_X; s];
end

mi = (1 + sum(test_X, 1))./length(image_test);% pass error zero
test_X = 1./ (1 + exp(test_X./repmat(mi, length(image_test), 1)));

test_X = [repmat([1], length(image_test),1) test_X];


ret = W'*test_X';

figure
[sret, sret_ind] = sort(ret, 'descend');
for i = 1:9
    filename = strcat(test_folder,'/', image_test(sret_ind(i)).name);
    image = imread(filename);
    subplot(3, 3, i);
    imshow(image);
    title(sprintf('Interestingness = %d', sret(i)));
end