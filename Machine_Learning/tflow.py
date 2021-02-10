import os
import tensorflow as tf
from tensorflow import keras
from model import *
from dataset import *

os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2' # ignore warning
physical_devices = tf.config.list_physical_devices('GPU')
try:
  tf.config.experimental.set_memory_growth(physical_devices[0], True)
except:
  print('Invalid device or cannot modify virtual devices once initialized.')


model = MNIST()
model.compile(
    loss=keras.losses.SparseCategoricalCrossentropy(from_logits=False),
    optimizer=keras.optimizers.Adam(learning_rate=1e-4),
    metrics=['accuracy']
)

model.fit(trainset, epochs=20,verbose=2)
model.evaluate(testset)
model.save('model/')