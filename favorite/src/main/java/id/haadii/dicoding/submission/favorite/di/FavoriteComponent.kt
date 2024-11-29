package id.haadii.dicoding.submission.favorite.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import id.haadii.dicoding.submission.favorite.FavoriteActivity
import id.hadi.dicoding.storyapp.di.FavoriteModuleDependencies

@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteComponent {
    fun inject(fragment: FavoriteActivity)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteModuleDependencies: FavoriteModuleDependencies): Builder
        fun build(): FavoriteComponent
    }
}