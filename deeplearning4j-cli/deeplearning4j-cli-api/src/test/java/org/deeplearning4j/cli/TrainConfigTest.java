/*
 *
 *  * Copyright 2015 Skymind,Inc.
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 */

package org.deeplearning4j.cli;

import org.apache.commons.io.FileUtils;
import org.deeplearning4j.cli.api.flags.Model;
import org.deeplearning4j.cli.subcommands.Train;
import org.deeplearning4j.models.featuredetectors.rbm.RBM;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.layers.factory.LayerFactories;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.junit.Test;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * @author sonali
 */
public class TrainConfigTest {
    @Test
    public void testInputModelTrain() throws Exception {
        Model testModelFlag = new Model();

        NeuralNetConfiguration conf = new NeuralNetConfiguration.Builder().iterationListener(new ScoreIterationListener(10))
                .dist(Nd4j.getDistributions().createNormal(1e-1,1))
                .layerFactory(LayerFactories.getFactory(RBM.class))
                .build();
        String json = conf.toJson();
        NeuralNetConfiguration testConfig = testModelFlag.value(json);
        assertEquals(conf, testConfig);

        FileUtils.writeStringToFile(new File("model.json"), json);

        String[] cmd = {
                "--input", "iris.txt", "--model", "model.json", "--output", "test_output.txt"
        };

        Train train = new Train(cmd);
    }
}
