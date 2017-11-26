/*
 * Copyright 2017, OpenSkywalking Organization All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project repository: https://github.com/OpenSkywalking/skywalking
 */

package org.skywalking.apm.collector.agent.stream.worker.trace.application;

import org.skywalking.apm.collector.core.module.ModuleManager;
import org.skywalking.apm.collector.queue.service.QueueCreatorService;
import org.skywalking.apm.collector.storage.StorageModule;
import org.skywalking.apm.collector.storage.base.dao.IPersistenceDAO;
import org.skywalking.apm.collector.storage.dao.IApplicationMappingPersistenceDAO;
import org.skywalking.apm.collector.storage.table.application.ApplicationMapping;
import org.skywalking.apm.collector.stream.worker.base.AbstractLocalAsyncWorkerProvider;
import org.skywalking.apm.collector.stream.worker.impl.PersistenceWorker;

/**
 * @author peng-yongsheng
 */
public class ApplicationMappingPersistenceWorker extends PersistenceWorker<ApplicationMapping, ApplicationMapping> {

    ApplicationMappingPersistenceWorker(ModuleManager moduleManager) {
        super(moduleManager);
    }

    @Override public int id() {
        return ApplicationMappingPersistenceWorker.class.hashCode();
    }

    @Override protected boolean needMergeDBData() {
        return true;
    }

    @Override protected IPersistenceDAO persistenceDAO() {
        return getModuleManager().find(StorageModule.NAME).getService(IApplicationMappingPersistenceDAO.class);
    }

    public static class Factory extends AbstractLocalAsyncWorkerProvider<ApplicationMapping, ApplicationMapping, ApplicationMappingPersistenceWorker> {

        public Factory(ModuleManager moduleManager, QueueCreatorService<ApplicationMapping> queueCreatorService) {
            super(moduleManager, queueCreatorService);
        }

        @Override public ApplicationMappingPersistenceWorker workerInstance(ModuleManager moduleManager) {
            return new ApplicationMappingPersistenceWorker(moduleManager);
        }

        @Override
        public int queueSize() {
            return 1024;
        }
    }
}
