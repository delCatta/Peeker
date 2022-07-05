require "test_helper"

class Documents::TagsControllerTest < ActionDispatch::IntegrationTest
  test "should get update" do
    get documents_tags_update_url
    assert_response :success
  end
end
