import tensorflow as tf
import tensorflow_datasets as tfds
from augmentation import *


AUTOTUNE = tf.data.experimental.AUTOTUNE
BATCH_SIZE = 32

(trainset, testset), data_info = tfds.load('mnist', 
                                               split=['train', 'test'],
                                               shuffle_files=False,
                                               as_supervised=True,  # will return tuple (img, label) otherwise dict
                                               with_info=True)  # able to get info about dataset
# Setup for train dataset
trainset = trainset.cache()
trainset = trainset.shuffle(data_info.splits["train"].num_examples)
trainset = trainset.map(normalize, num_parallel_calls=AUTOTUNE)
trainset = trainset.map(augment, num_parallel_calls=AUTOTUNE)
trainset = trainset.batch(BATCH_SIZE)
trainset = trainset.prefetch(AUTOTUNE)

# Setup for test Dataset
testset = testset.map(normalize, num_parallel_calls=AUTOTUNE)
testset = testset.batch(BATCH_SIZE)
testset = testset.prefetch(AUTOTUNE)