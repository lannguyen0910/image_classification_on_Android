# Image Classification model trained on MNIST dataset
- First time using Tensorflow, it was pretty fast and convenient, all we have to do is to remember the functions, how to it works and steps to train a model.

## Things to note
- Tensorflow version==2.1 or else we can't use addons.
- @tf.function for faster and better use.
- convert to TFLite.
- The accuracy must be over 98% after 20 epochs.

## Run
- For training
```
python tflow.py
```

- For convert from Tensorflow to TFLite
```
python tensorflow_lite.py
```

## Sources
- https://github.com/aladdinpersson/HelloMnist/blob/main/TensorFlowCode/main.py
- https://www.tensorflow.org/