class Document < ApplicationRecord
  belongs_to :user
  scope :favorites, -> { where(favorite: true) }
  enum document_type: %i[passport dni]
  has_one_attached :document
  acts_as_taggable_on :tags
end
