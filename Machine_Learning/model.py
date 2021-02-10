from keras import layers
from tensorflow import keras
from tensorflow.keras import Model

def MNIST():
    inputs = keras.Input(shape=(28, 28, 1))
    x = layers.Conv2D(32, 3)(inputs) # 3 is number of image channel
    x = layers.BatchNormalization()(x)
    x = keras.activations.relu(x)
    x = layers.MaxPooling2D()(x)

    x = layers.Conv2D(64, 3)(inputs)
    x = layers.BatchNormalization()(x)
    x = keras.activations.relu(x)
    x = layers.MaxPooling2D()(x)

    x = layers.Conv2D(128, 3)(inputs)
    x = layers.BatchNormalization()(x)
    x = keras.activations.relu(x)

    x = layers.Flatten()(x)
    x = layers.Dense(64, activation='relu')(x)
    outputs = layers.Dense(10, activation='softmax')(x)

    return Model(inputs=inputs, outputs=outputs)