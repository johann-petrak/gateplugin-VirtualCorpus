/*
 * Copyright (c) 2010- Austrian Research Institute for Artificial Intelligence (OFAI). 
 * Copyright (C) 2014-2016 The University of Sheffield.
 *
 * This file is part of gateplugin-VirtualCorpus
 * (see https://github.com/johann-petrak/gateplugin-VirtualCorpus)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software. If not, see <http://www.gnu.org/licenses/>.
 */
package at.ofai.gate.virtualcorpus;

import gate.DataStore;
import gate.creole.ResourceInstantiationException;
import gate.persist.PersistenceException;
import gate.util.persistence.LRPersistence;

/**
 * Persistence for the DirectoryCorpus LR.
 * The standard Corpus Persistence won't do as it either expects a persistent
 * corpus in which case it must have a Datastore, or a transient corpus in
 * which case all the documents are serialized too. We do not want either and
 * just serialize the initialization parameters so the LR will be recreated
 * in an identical way when loaded.
 * 
 * @author Johann Petrak
 */
public class DirectoryCorpusPersistence extends LRPersistence {
  public static final long serialVersionUID = 1L;
  /**
   * Populates this Persistence with the data that needs to be stored from the
   * original source object.
   */
  @Override
  public void extractDataFromSource(Object source)
    throws PersistenceException{
    if(! (source instanceof DirectoryCorpus)){
      throw new UnsupportedOperationException(
                getClass().getName() + " can only be used for " +
                DirectoryCorpus.class.getName() +
                " objects!\n" + source.getClass().getName() +
                " is not a " + DirectoryCorpus.class.getName());
    }

    DirectoryCorpus corpus = (DirectoryCorpus)source;
    DataStore ds = corpus.getDataStore();
    super.extractDataFromSource(source);
    corpus.setDataStore(ds);
  }


  /**
   * Creates a new object from the data contained. This new object is supposed
   * to be a copy for the original object used as source for data extraction.
   */
  @Override
  public Object createObject()throws PersistenceException,
                                     ResourceInstantiationException{
    DirectoryCorpus corpus = (DirectoryCorpus)super.createObject();
    return corpus;
  }
}

