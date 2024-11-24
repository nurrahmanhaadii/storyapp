package id.haadii.dicoding.submission.domain.model


data class StoryBase(
    val error: Boolean,
    val listStory: List<Story>,
    val story: Story,
    val message: String
)
