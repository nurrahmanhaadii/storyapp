package id.hadi.dicoding.storyapp.domain.model


data class StoryBase(
    val error: Boolean,
    val listStory: List<Story>,
    val story: Story,
    val message: String
)
