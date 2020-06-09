package manu.com.inject.di;

import dagger.Component;
import manu.com.inject.HeroManager;

/**
 * @Desc:
 * @Author: Administrator
 * @Date: 2020/6/1 15:23.
 */
@Component(modules = HeroManagerModule.class)
public interface HeroManagerComponent {
    void injectHeroManager(HeroManager manager);
}
