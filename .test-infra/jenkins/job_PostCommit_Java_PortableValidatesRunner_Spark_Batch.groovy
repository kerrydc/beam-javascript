/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import CommonJobProperties as commonJobProperties
import PostcommitJobBuilder

// This job runs the suite of Java ValidatesRunner tests against the Spark runner in batch mode.
PostcommitJobBuilder.postCommitJob('beam_PostCommit_Java_PVR_Spark_Batch',
    'Run Java Spark PortableValidatesRunner Batch', 'Java Spark PortableValidatesRunner Batch Tests', this) {
      description('Runs the Java PortableValidatesRunner suite on the Spark runner in batch mode.')

      // Set common parameters.
      commonJobProperties.setTopLevelMainJobProperties(delegate, 'master', 240)

      // Publish all test results to Jenkins
      publishers {
        archiveJunit('**/build/test-results/**/*.xml')
      }

      // Gradle goals for this job.
      steps {
        gradle {
          rootBuildScriptDir(commonJobProperties.checkoutDir)
          tasks(':runners:spark:2:job-server:validatesPortableRunnerBatch')
          tasks(':runners:spark:3:job-server:validatesPortableRunnerBatch')
          commonJobProperties.setGradleSwitches(delegate)
        }
      }
    }
