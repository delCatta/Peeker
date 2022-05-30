class Document < ApplicationRecord
  belongs_to :user
  scope :favorites, -> { where(favorite: true) }
  has_one_attached :document
  acts_as_taggable_on :tags
end
