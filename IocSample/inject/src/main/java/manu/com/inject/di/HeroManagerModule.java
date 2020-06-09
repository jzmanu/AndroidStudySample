package manu.com.inject.di;

import dagger.Module;
import dagger.Provides;
import manu.com.inject.Hero;

/**
 * @Desc:
 * @Author: Administrator
 * @Date: 2020/6/1 15:23.
 */
@Module
public class HeroManagerModule {
    @Provides
    public Hero providerHero(){
        return new Hero();
    }
}
