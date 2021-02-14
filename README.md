# image_classification_on_Android
Image Classification with MNIST dataset on Android to detect one-digit numbers

## Structure
Machine Learning:
- Use Tensorflow to train digits then convert to TFLite to use for display confidence score in Android
- Must use Tensorflow version 2.1 (to use TF-Addons)

Android: Consists 2 parts
- Draw part: Use FingerPaintView to draw digits and 2 buttons to show predict score and clear I/O
- Image part: Upload image and predict


## Download
Prebuilt APK can be downloaded from [here](download/hello_mnist.apk).

## Result
![result](result/draw.gif)

![result](result/image.PNG)
![result](result/image2.PNG)

## Sources
- Inspire from [AladdinPersson](https://github.com/aladdinpersson/HelloMnist)
- Draw: [nex3z](https://github.com/nex3z/tflite-mnist-android)
- PreprocessImage: [vasugargofficial](https://github.com/vasugargofficial/Image-Classification-Mobilenet-AndroidDemo/blob/master/app/src/main/java/com/example/imageclassificationdemo/MainActivity.java)