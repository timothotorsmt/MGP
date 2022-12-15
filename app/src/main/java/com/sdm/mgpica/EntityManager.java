package com.sdm.mgpica;

import android.graphics.Canvas;
import android.view.SurfaceView;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

// Created by TanSiewLan2021

public class EntityManager {

    public final static EntityManager Instance = new EntityManager();
    private LinkedList<EntityBase> entityList = new LinkedList<EntityBase>();
    private SurfaceView view = null;

    private EntityManager()
    {
    }

    public void Init(SurfaceView _view)
    {
        view = _view;
    }

    public void Update(float _dt)
    {
        LinkedList<EntityBase> removalList = new LinkedList<EntityBase>();

        // Update all
        for(int i = 0; i < entityList.size(); ++i)
        {
            // Lets check if is init, initialize if not
            if (!entityList.get(i).IsInit())
            {
                entityList.get(i).Init(view);
            }

            entityList.get(i).Update(_dt);

            // Check if need to clean up
            if (entityList.get(i).IsDone()) {
                // Done! Time to add to the removal list
                removalList.add(entityList.get(i));
            }
            removalList.clear(); // Clean up of removal list
        }
    }

    public void Render(Canvas _canvas)
    {
      
        // Use the new "rendering layer" to sort the render order
        Collections.sort(entityList, new Comparator<EntityBase>() {
            @Override
            public int compare(EntityBase o1, EntityBase o2) {
                return o1.GetRenderLayer() - o2.GetRenderLayer();
            }
        });

        for(int i = 0; i <entityList.size(); ++i)
        {
            entityList.get(i).Render(_canvas);
        }
    }

    public void AddEntity(EntityBase _newEntity, EntityBase.ENTITY_TYPE entity_type)
    {
        entityList.add(_newEntity);
    }

    public void Clean()
    {
        entityList.clear();
    }
}


