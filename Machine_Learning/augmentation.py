import tensorflow as tf
import math
import tensorflow_addons as tfa


@tf.function
def normalize(image,label):
    return tf.cast(image, tf.float32), label    # images are between 0 and 1

@tf.function
def rotate(image, max_degrees=25):
    degrees = tf.random.uniform([], -max_degrees, max_degrees, dtype=tf.float32)
    image = tfa.image.rotate(image, degrees * math.pi / 180, interpolation='BILINEAR')
    return image

@tf.function
def augment(image, label):
    image  = tf.image.resize(image, size = [28, 28])
    image = rotate(image)

    # random coloring for images
    image = tf.image.random_brightness(image, max_delta=0.2)
    image = tf.image.random_contrast(image, lower=0.5, upper=1.5)
    return image, label