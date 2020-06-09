package manu.com.inject;

import javax.inject.Inject;

import manu.com.inject.di.DaggerHeroManagerComponent;

/**
 * @Desc:
 * @Author: Administrator
 * @Date: 2020/6/1 14:36.
 */
public class HeroManager {
    private static HeroManager instance;

    static {
        instance = new HeroManager();
    }

    public static HeroManager getInstance(){
        return instance;
    }

    @Inject
    Hero hero;

    public void start(){
        DaggerHeroManagerComponent.create().injectHeroManager(this);
        hero.shoot();
    }
}
