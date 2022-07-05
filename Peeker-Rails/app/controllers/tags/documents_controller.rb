class Tags::DocumentsController < ApplicationController
  before_action :set_tag
  
  def index
    @documents = @tag.documents
  end

  private 
    def set_tag
      @tag = Current.user.tags.find(params[:tag_id])
    end
end
